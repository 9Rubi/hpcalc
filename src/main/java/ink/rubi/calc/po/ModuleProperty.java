package ink.rubi.calc.po;

import ink.rubi.calc.controller.constant.ModuleFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SingleSelectionModel;

/**
 * @author Henry Wang
 * @since 2019-06-17 15:17
 */
public class ModuleProperty {
    private StringProperty level = new SimpleStringProperty();
    private ObjectProperty<SingleSelectionModel<ModuleFactory.BetaFactory>> beta = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<ModuleFactory.GammaFactory>> gamma = new SimpleObjectProperty<>();

    public String getLevel() {
        return level.get();
    }

    public StringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public SingleSelectionModel<ModuleFactory.BetaFactory> getBeta() {
        return beta.get();
    }

    public ObjectProperty<SingleSelectionModel<ModuleFactory.BetaFactory>> betaProperty() {
        return beta;
    }

    public void setBeta(SingleSelectionModel<ModuleFactory.BetaFactory> beta) {
        this.beta.set(beta);
    }

    public SingleSelectionModel<ModuleFactory.GammaFactory> getGamma() {
        return gamma.get();
    }

    public ObjectProperty<SingleSelectionModel<ModuleFactory.GammaFactory>> gammaProperty() {
        return gamma;
    }

    public void setGamma(SingleSelectionModel<ModuleFactory.GammaFactory> gamma) {
        this.gamma.set(gamma);
    }
}
