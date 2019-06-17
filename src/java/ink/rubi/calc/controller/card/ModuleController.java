package ink.rubi.calc.controller.card;

import ink.rubi.calc.controller.constant.ModuleFactory;
import ink.rubi.calc.controller.coventer.IntegerConverter;
import ink.rubi.calc.controller.coventer.ModuleBetaFactoryItemConverter;
import ink.rubi.calc.controller.coventer.ModuleGammaFactoryItemConverter;
import ink.rubi.calc.po.ModuleProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Henry Wang
 * @since 2019-06-17 13:04
 */
@Getter
public class ModuleController implements Initializable {
    @FXML
    private TextField level;
    @FXML
    private ComboBox<ModuleFactory.BetaFactory> beta;
    @FXML
    private ComboBox<ModuleFactory.GammaFactory> gamma;

    private ModuleProperty module = new ModuleProperty();

    private IntegerProperty moduleLevel = new SimpleIntegerProperty();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        beta.converterProperty().set(new ModuleBetaFactoryItemConverter());
        gamma.converterProperty().set(new ModuleGammaFactoryItemConverter());

        beta.getItems().addAll(ModuleFactory.BetaFactory.values());
        gamma.getItems().addAll(ModuleFactory.GammaFactory.values());

        module.betaProperty().bindBidirectional(beta.selectionModelProperty());
        module.gammaProperty().bindBidirectional(gamma.selectionModelProperty());
        module.levelProperty().bindBidirectional(level.textProperty());


        Bindings.bindBidirectional(module.levelProperty(), moduleLevel, new IntegerConverter());


        initValue();
    }

    public void initValue() {
        level.setText("90");
        beta.setValue(ModuleFactory.BetaFactory.NONE);
        gamma.setValue(ModuleFactory.GammaFactory.NONE);
    }
}
