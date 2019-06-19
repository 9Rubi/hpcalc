package ink.rubi.calc.controller;

import ink.rubi.calc.controller.calc.Calculator;
import ink.rubi.calc.controller.card.ModuleController;
import ink.rubi.calc.controller.card.ShieldController;
import ink.rubi.calc.controller.constant.ModuleFactory;
import ink.rubi.calc.controller.constant.Reality;
import ink.rubi.calc.controller.constant.ShieldFactory;
import ink.rubi.calc.modal.AlertBox;
import ink.rubi.calc.po.ModuleProperty;
import ink.rubi.calc.po.ShieldProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Rubi
 * @since 2019-06-17 12:46
 */
@Slf4j
public class MainController implements Initializable {

    @FXML
    private TextField hp, hpPower;
    @FXML
    private Label capacityVal, speedVal, delayVal, maxHpDecreaseVal, maxHpIncrease, recovery, finalHp;
    @FXML
    private ModuleController moduleCardController;
    @FXML
    private ShieldController shieldCardController;


    public void initValue() {
        hp.setText("1410475");
        hpPower.setText("1.00");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initValue();

    }

    @SuppressWarnings("all")
    public void doCalc(ActionEvent actionEvent) {
        final ShieldProperty shield = shieldCardController.getShield();
        final ShieldFactory shieldAlpha = shield.getAlpha().selectedItemProperty().get();
        final ShieldFactory shieldBeta = shield.getBeta().selectedItemProperty().get();
        final ShieldFactory shieldGamma = shield.getGamma().selectedItemProperty().get();
        final Reality reality = shield.getReality().selectedItemProperty().get();

        final ModuleProperty module = moduleCardController.getModule();
        final ModuleFactory.BetaFactory moduleBeta = module.getBeta().selectedItemProperty().get();
        final ModuleFactory.GammaFactory moduleGamma = module.getGamma().selectedItemProperty().get();

        final int shieldLevel = shieldCardController.getShieldLevel().get();

        final int moduleLevel = moduleCardController.getModuleLevel().get();

        final int rateMaxHp = shieldAlpha.getMaxHpDecrease() +
                shieldBeta.getMaxHpDecrease() +
                shieldGamma.getMaxHpDecrease() +
                reality.getMaxHpDecrease();
        final int rateCapacity = shieldAlpha.getCapacity() + shieldBeta.getCapacity() + shieldGamma.getCapacity() + reality.getCapacity() + 7500;
        final int rateSpeed = shieldAlpha.getSpeed() + shieldBeta.getSpeed() + shieldGamma.getSpeed() + reality.getSpeed();
        final int rateDelay = shieldAlpha.getDelay() + shieldBeta.getDelay() + shieldGamma.getDelay();
        final int rateMaxHpIncrease = moduleBeta.getMaxHpIncrease();
        final int rateRecovery = moduleGamma.getRecovery();
        BigDecimal calcCapacity = Calculator.calcCapacity(rateCapacity, shieldLevel);
        BigDecimal calcSpeed = Calculator.calcSpeed(rateSpeed, shieldLevel);
        BigDecimal calcDelay = Calculator.calcDelay(rateDelay);
        BigDecimal calcMaxHpDecrease = Calculator.calcMaxHpDecrease(rateMaxHp, shieldLevel);
        BigDecimal calcMaxHpIncrease = Calculator.calcMaxHpIncrease(rateMaxHpIncrease, moduleLevel);
        BigDecimal calcRecovery = Calculator.calcRecovery(rateRecovery, moduleLevel);
        BigDecimal calcFinal = Calculator.calcFinal(new BigDecimal(hp.getText()), calcMaxHpDecrease,
                new BigDecimal(hpPower.getText()), calcMaxHpIncrease);
        /*护盾部分*/
        capacityVal.setText(calcCapacity.toString());
        speedVal.setText(calcSpeed.toString());
        delayVal.setText(calcDelay.toString());
        maxHpDecreaseVal.setText(calcMaxHpDecrease.toString());
        /*模组部分*/
        maxHpIncrease.setText(calcMaxHpIncrease.toString());
        recovery.setText(calcRecovery.toString());
        /*最后结果*/
        finalHp.setText(calcFinal.toString());
    }


    public void calcForEach(ActionEvent actionEvent) {
        show();
    }


    private void show() {
        log.info("======================================================================");
        final int hpMax = Integer.parseInt(hp.getText());
        final double bonus = Double.parseDouble(hpPower.getText());
        List<String> results = new ArrayList<>();
        int thereIs = 0;
        int totalIs = 0;
        for (int shieldLevel = 90; shieldLevel >= 1; shieldLevel--) {
            for (int moduleLevel = 90; moduleLevel >= 1; moduleLevel--) {
                loop:
                for (ModuleFactory.BetaFactory betaFactory : Calculator.BETA_FACTORIES) {
                    List<List<ShieldFactory>> lists = new ArrayList<>();
                    for (ShieldFactory alpha : Calculator.SHIELD_FACTORIES) {
                        for (ShieldFactory beta : Calculator.SHIELD_FACTORIES) {
                            for (ShieldFactory gamma : Calculator.SHIELD_FACTORIES) {
                                for (List<ShieldFactory> list : lists) {
                                    if (list.contains(alpha) && list.contains(beta) && list.contains(gamma)) {
                                        continue loop;
                                    }
                                }
                                lists.add(new ArrayList<ShieldFactory>() {{
                                    add(alpha);
                                    add(beta);
                                    add(gamma);
                                }});
                                for (Reality reality : Calculator.REALITIES) {
                                    String result = doForEach(shieldLevel, moduleLevel, alpha, beta, gamma, reality, betaFactory, hpMax, bonus);
                                    if (result != null) {
                                        results.add(result);
                                        thereIs++;
                                    }
                                    totalIs++;
                                }
                            }
                        }
                    }
                }
            }
        }
        results.add("======================================================================");
        results.add(String.format("总共遍历了%s种,其中有%s种合适(最终血量4~1000)的组合", totalIs, thereIs));
        String totalStr = String.join("\n", results);
        AlertBox.show("结果", totalStr);

    }

    private String doForEach(final int shieldLevel, final int moduleLevel,
                             final ShieldFactory alpha, final ShieldFactory beta, final ShieldFactory gamma,
                             final Reality reality, final ModuleFactory.BetaFactory betaFactory,
                             final int hpMax, final double bonus) {
        int rateMaxHp = alpha.getMaxHpDecrease() +
                beta.getMaxHpDecrease() +
                gamma.getMaxHpDecrease() +
                reality.getMaxHpDecrease();
        BigDecimal maxHpDecreaseResult = Calculator.calcMaxHpDecrease(rateMaxHp, shieldLevel);
        BigDecimal maxHpIncreaseResult = Calculator.calcMaxHpIncrease(betaFactory.getMaxHpIncrease(), moduleLevel);

        BigDecimal result = Calculator.calcFinal(BigDecimal.valueOf(hpMax), maxHpDecreaseResult,
                BigDecimal.valueOf(bonus), maxHpIncreaseResult);
        int intValue = result.intValue();
        if (4 <= intValue && intValue <= 1000) {
            return String.format("护盾等级：%s,配件：%s|%s|%s,稀有度：%s,模组等级为：%s,配件：%s,最终血量：%s",
                    shieldLevel, alpha, beta, gamma, reality.getName(), moduleLevel, betaFactory, intValue);

        }
        return null;
    }


}
