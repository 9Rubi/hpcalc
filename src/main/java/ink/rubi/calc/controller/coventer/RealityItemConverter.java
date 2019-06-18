package ink.rubi.calc.controller.coventer;

import ink.rubi.calc.controller.constant.Reality;
import javafx.util.StringConverter;

/**
 * @author Henry Wang
 * @since 2019-06-17 14:34
 */
public class RealityItemConverter extends StringConverter<Reality> {
    @Override
    public String toString(Reality object) {
        return object.getName();
    }

    @Override
    public Reality fromString(String string) {
        return null;
    }
}
