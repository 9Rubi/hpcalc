package ink.rubi.calc.controller;

import ink.rubi.calc.controller.card.ModuleController;
import ink.rubi.calc.controller.card.ShieldController;
import ink.rubi.calc.controller.coventer.IntegerConverter;
import ink.rubi.calc.po.ModuleProperty;
import ink.rubi.calc.po.ShieldProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Henry Wang
 * @since 2019-06-17 12:46
 */
public class MainController implements Initializable {
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
        ModuleProperty module = moduleCardController.getModule();
        ShieldProperty shield = shieldCardController.getShield();
        IntegerConverter converter = new IntegerConverter();



        Bindings.bindBidirectional(shield.levelProperty(), shieldLevel,converter);
        Bindings.bindBidirectional(module.levelProperty(), moduleLevel,converter);
        capacityVal.textProperty().bind(shieldLevel.add(moduleLevel).asString());
    }


}
