package ink.rubi.calc.controller.coventer;

import ink.rubi.calc.controller.constant.ShieldFactory;
import javafx.util.StringConverter;

/**
 * @author Henry Wang
 * @since 2019-06-17 14:26
 */
public class ShieldFactoryItemConverter extends StringConverter<ShieldFactory> {
    @Override
    public String toString(ShieldFactory object) {
        return object.getName();
    }

    @Override
    public ShieldFactory fromString(String string) {
        return null;
    }
}
