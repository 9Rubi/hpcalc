package ink.rubi.calc.po;

import ink.rubi.calc.controller.constant.Reality;
import ink.rubi.calc.controller.constant.ShieldFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SingleSelectionModel;

/**
 * @author Henry Wang
 * @since 2019-06-17 13:28
 */
public class ShieldProperty {
    private StringProperty level = new SimpleStringProperty();
    private ObjectProperty<SingleSelectionModel<ShieldFactory>> alpha = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<ShieldFactory>> beta = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<ShieldFactory>> gamma = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<Reality>> reality = new SimpleObjectProperty<>();

    public String getLevel() {
        return level.get();
    }

    public StringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public SingleSelectionModel<ShieldFactory> getAlpha() {
        return alpha.get();
    }

    public ObjectProperty<SingleSelectionModel<ShieldFactory>> alphaProperty() {
        return alpha;
    }

    public void setAlpha(SingleSelectionModel<ShieldFactory> alpha) {
        this.alpha.set(alpha);
    }

    public SingleSelectionModel<ShieldFactory> getBeta() {
        return beta.get();
    }

    public ObjectProperty<SingleSelectionModel<ShieldFactory>> betaProperty() {
        return beta;
    }

    public void setBeta(SingleSelectionModel<ShieldFactory> beta) {
        this.beta.set(beta);
    }

    public SingleSelectionModel<ShieldFactory> getGamma() {
        return gamma.get();
    }

    public ObjectProperty<SingleSelectionModel<ShieldFactory>> gammaProperty() {
        return gamma;
    }

    public void setGamma(SingleSelectionModel<ShieldFactory> gamma) {
        this.gamma.set(gamma);
    }

    public SingleSelectionModel<Reality> getReality() {
        return reality.get();
    }

    public ObjectProperty<SingleSelectionModel<Reality>> realityProperty() {
        return reality;
    }

    public void setReality(SingleSelectionModel<Reality> reality) {
        this.reality.set(reality);
    }
}
