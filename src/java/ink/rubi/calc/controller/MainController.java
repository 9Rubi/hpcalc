package ink.rubi.calc.controller;

import ink.rubi.calc.controller.card.ModuleController;
import ink.rubi.calc.controller.card.ShieldController;
import ink.rubi.calc.controller.constant.ModuleFactory;
import ink.rubi.calc.controller.constant.Reality;
import ink.rubi.calc.controller.constant.ShieldFactory;
import ink.rubi.calc.po.ModuleProperty;
import ink.rubi.calc.po.ShieldProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    private Label capacityVal, speedVal, delayVal, maxHpDecreaseVal, maxHpIncrease, recovery;
    @FXML
    private ModuleController moduleCardController;
    @FXML
    private ShieldController shieldCardController;


    private IntegerProperty shieldLevel = new SimpleIntegerProperty();
    private IntegerProperty moduleLevel = new SimpleIntegerProperty();

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    /*    ModuleProperty module = moduleCardController.getModule();
        ShieldProperty shield = shieldCardController.getShield();
        IntegerConverter converter = new IntegerConverter();



        Bindings.bindBidirectional(shield.levelProperty(), shieldLevel,converter);
        Bindings.bindBidirectional(module.levelProperty(), moduleLevel,converter);
        capacityVal.textProperty().bind(shieldLevel.add(moduleLevel).asString());*/
    }

    public void doCalc(ActionEvent actionEvent) {
        BigDecimal staticVar = new BigDecimal(10000);
        BigDecimal perLevel = new BigDecimal(1.13);
        BigDecimal speedPower = new BigDecimal(10.24);
        BigDecimal delayPower = new BigDecimal(3.5);
        BigDecimal maxHpDecreasePower = new BigDecimal(32);
        BigDecimal capacityPower = new BigDecimal(64);


        BigDecimal maxHpIncreasePower = new BigDecimal(16);
        BigDecimal recoveryPower = new BigDecimal(0.5);


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


        int rateMaxHp = shieldAlpha.getMaxHpDecrease() + shieldBeta.getMaxHpDecrease() + shieldGamma.getMaxHpDecrease() + reality.getMaxHpDecrease();
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
        System.out.println(capacityResult);
        System.out.println(speedResult);
        System.out.println(delayResult);
        System.out.println(maxHpDecreaseResult);

        BigDecimal maxHpIncreaseResult = new BigDecimal(moduleBeta.getMaxHpIncrease()).divide(staticVar).add(BigDecimal.ONE)
                .multiply(maxHpIncreasePower)
                .multiply(perLevel.pow(Integer.valueOf(moduleLevel)))
                .setScale(0, RoundingMode.HALF_UP);
        BigDecimal recoveryResult = new BigDecimal(moduleGamma.getRecovery()).divide(staticVar).add(BigDecimal.ONE)
                .multiply(recoveryPower)
                .multiply(perLevel.pow(Integer.valueOf(moduleLevel)))
                .setScale(0, RoundingMode.HALF_UP);


        System.out.println(maxHpIncreaseResult);
        System.out.println(recoveryResult);
        int result = (530564 - maxHpDecreaseResult.intValue()) * 1 + maxHpIncreaseResult.intValue();
        System.out.println(result);
    }


}
