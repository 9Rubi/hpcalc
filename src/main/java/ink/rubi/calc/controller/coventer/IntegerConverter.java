package ink.rubi.calc.controller.coventer;

import javafx.util.StringConverter;

/**
 * @author Henry Wang
 * @since 2019-06-17 17:11
 */
public class IntegerConverter extends StringConverter<Number> {

    @Override
    public String toString(Number object) {
        return String.valueOf(object);
    }

    @Override
    public Number fromString(String string) {
        return Integer.valueOf(string);
    }
}
