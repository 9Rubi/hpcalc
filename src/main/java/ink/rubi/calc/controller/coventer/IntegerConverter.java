package ink.rubi.calc.controller.coventer;

import javafx.util.StringConverter;

/**
 * @author Rubi
 * @since 2019-06-17 17:11
 */
public class IntegerConverter extends StringConverter<Number> {

    @Override
    public String toString(Number object) {
        return String.valueOf(object);
    }

    @Override
    public Number fromString(String string) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException e) {
            return 0;
        }

    }
}
