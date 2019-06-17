package ink.rubi.calc.controller;

import ink.rubi.calc.controller.card.ModuleController;
import ink.rubi.calc.controller.card.ShieldController;
import ink.rubi.calc.controller.constant.ModuleFactory;
import ink.rubi.calc.controller.constant.Reality;
import ink.rubi.calc.controller.constant.ShieldFactory;
import ink.rubi.calc.po.ModuleProperty;
import ink.rubi.calc.po.ShieldProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Henry Wang
 * @since 2019-06-17 12:46
 */
public class MainController implements Initializable {

    public Button calc;
    @FXML
    private TextField hp, hpPower;
    @FXML
    private Label capacityVal, speedVal, delayVal, maxHpDecreaseVal, maxHpIncrease, recovery, finalHp;
    @FXML
    private ModuleController moduleCardController;
    @FXML
    private ShieldController shieldCardController;

    private static final BigDecimal staticVar = new BigDecimal(10000);
    private static final BigDecimal perLevel = new BigDecimal(1.13);
    private static final BigDecimal speedPower = new BigDecimal(10.24);
    private static final BigDecimal delayPower = new BigDecimal(3.5);
    private static final BigDecimal maxHpDecreasePower = new BigDecimal(32);
    private static final BigDecimal capacityPower = new BigDecimal(64);
    private static final BigDecimal maxHpIncreasePower = new BigDecimal(16);
    private static final BigDecimal recoveryPower = new BigDecimal(0.5);


    public void initValue() {
        hp.setText("1410475");
        hpPower.setText("1");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initValue();

    }

    public void doCalc(ActionEvent actionEvent) {
        ShieldProperty shield = shieldCardController.getShield();
        ShieldFactory shieldAlpha = shield.getAlpha().selectedItemProperty().get();
        ShieldFactory shieldBeta = shield.getBeta().selectedItemProperty().get();
        ShieldFactory shieldGamma = shield.getGamma().selectedItemProperty().get();
        Reality reality = shield.getReality().selectedItemProperty().get();
        String shieldLevel = shield.getLevel();

        ModuleProperty module = moduleCardController.getModule();
        ModuleFactory.BetaFactory moduleBeta = module.getBeta().selectedItemProperty().get();
        ModuleFactory.GammaFactory moduleGamma = module.getGamma().selectedItemProperty().get();
        String moduleLevel = module.getLevel();

        int rateMaxHp = shieldAlpha.getMaxHpDecrease() +
                shieldBeta.getMaxHpDecrease() +
                shieldGamma.getMaxHpDecrease() +
                reality.getMaxHpDecrease();
        BigDecimal maxHpDecreaseResult = new BigDecimal(rateMaxHp)
                .divide(staticVar).add(BigDecimal.ONE)
                .multiply(maxHpDecreasePower)
                .multiply(perLevel.pow(Integer.valueOf(shieldLevel)))
                .setScale(0, RoundingMode.HALF_UP);

        int rateCapacity = shieldAlpha.getCapacity() + shieldBeta.getCapacity() + shieldGamma.getCapacity() + reality.getCapacity() + 7500;
        BigDecimal capacityResult = new BigDecimal(rateCapacity)
                .divide(staticVar).add(BigDecimal.ONE)
                .multiply(capacityPower)
                .multiply(perLevel.pow(Integer.valueOf(shieldLevel)))
                .setScale(0, RoundingMode.HALF_UP);

        int rateSpeed = shieldAlpha.getSpeed() + shieldBeta.getSpeed() + shieldGamma.getSpeed() + reality.getSpeed();
        BigDecimal speedResult = new BigDecimal(rateSpeed)
                .divide(staticVar);

        if (speedResult.compareTo(BigDecimal.ZERO) >= 0) {
            speedResult = speedPower
                    .multiply(perLevel.pow(Integer.valueOf(shieldLevel)))
                    .multiply(speedResult.add(BigDecimal.ONE))
                    .setScale(0, RoundingMode.HALF_UP);
        } else {
            speedResult = speedPower
                    .multiply(perLevel.pow(Integer.valueOf(shieldLevel)))
                    .divide(speedResult.abs().add(BigDecimal.ONE), 0, RoundingMode.HALF_UP);
        }

        int rateDelay = shieldAlpha.getDelay() + shieldBeta.getDelay() + shieldGamma.getDelay();

        BigDecimal delayResult = new BigDecimal(rateDelay)
                .divide(staticVar);
        if (delayResult.compareTo(BigDecimal.ZERO) >= 0) {
            delayResult = delayPower.multiply(delayResult.add(BigDecimal.ONE)).setScale(2, RoundingMode.HALF_UP);
        } else {
            delayResult = delayPower.divide(
                    delayResult.abs().add(BigDecimal.ONE), 2, RoundingMode.HALF_UP
            );
        }
        capacityVal.setText(capacityResult.toString());
        speedVal.setText(speedResult.toString());
        delayVal.setText(delayResult.toString());
        maxHpDecreaseVal.setText(maxHpDecreaseResult.toString());

        BigDecimal maxHpIncreaseResult = new BigDecimal(moduleBeta.getMaxHpIncrease()).divide(staticVar).add(BigDecimal.ONE)
                .multiply(maxHpIncreasePower)
                .multiply(perLevel.pow(Integer.valueOf(moduleLevel)))
                .setScale(0, RoundingMode.HALF_UP);
        BigDecimal recoveryResult = new BigDecimal(moduleGamma.getRecovery()).divide(staticVar).add(BigDecimal.ONE)
                .multiply(recoveryPower)
                .multiply(perLevel.pow(Integer.valueOf(moduleLevel)))
                .setScale(0, RoundingMode.HALF_UP);

        maxHpIncrease.setText(maxHpIncreaseResult.toString());
        recovery.setText(recoveryResult.toString());

        BigDecimal result = new BigDecimal(hp.getText())
                .subtract(maxHpDecreaseResult)
                .multiply(new BigDecimal(hpPower.getText()))
                .add(maxHpIncreaseResult);

        finalHp.setText(result.toString());
    }


}
