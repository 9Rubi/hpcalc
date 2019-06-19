package ink.rubi.calc.controller.card;

import ink.rubi.calc.controller.constant.Reality;
import ink.rubi.calc.controller.constant.ShieldFactory;
import ink.rubi.calc.controller.coventer.IntegerConverter;
import ink.rubi.calc.controller.coventer.RealityItemConverter;
import ink.rubi.calc.controller.coventer.ShieldFactoryItemConverter;
import ink.rubi.calc.po.ShieldProperty;
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
 * @author Rubi
 * @since 2019-06-17 13:04
 */
@Getter
public class ShieldController implements Initializable {
    @FXML
    private ComboBox<Reality> reality;
    @FXML
    private ComboBox<ShieldFactory> alpha, beta, gamma;
    @FXML
    private TextField level;

    private ShieldProperty shield = new ShieldProperty();

    private IntegerProperty shieldLevel = new SimpleIntegerProperty();


    public void initValue() {
        level.setText("90");
        alpha.setValue(ShieldFactory.NONE);
        beta.setValue(ShieldFactory.NONE);
        gamma.setValue(ShieldFactory.NONE);
        reality.setValue(Reality.WHITE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ShieldFactoryItemConverter converter = new ShieldFactoryItemConverter();

        alpha.converterProperty().set(converter);
        beta.converterProperty().set(converter);
        gamma.converterProperty().set(converter);
        reality.converterProperty().set(new RealityItemConverter());

        alpha.getItems().addAll(ShieldFactory.values());
        beta.getItems().addAll(ShieldFactory.values());
        gamma.getItems().addAll(ShieldFactory.values());
        reality.getItems().addAll(Reality.values());

        shield.alphaProperty().bindBidirectional(alpha.selectionModelProperty());
        shield.betaProperty().bindBidirectional(beta.selectionModelProperty());
        shield.gammaProperty().bindBidirectional(gamma.selectionModelProperty());
        shield.realityProperty().bindBidirectional(reality.selectionModelProperty());
        shield.levelProperty().bindBidirectional(level.textProperty());

        Bindings.bindBidirectional(shield.levelProperty(), shieldLevel, new IntegerConverter());


        initValue();
    }
}
