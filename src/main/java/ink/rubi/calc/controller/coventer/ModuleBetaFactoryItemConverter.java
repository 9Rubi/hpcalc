package ink.rubi.calc.controller.coventer;

import ink.rubi.calc.controller.constant.ModuleFactory;
import javafx.util.StringConverter;

/**
 * @author Rubi
 * @since 2019-06-17 15:22
 */
public class ModuleBetaFactoryItemConverter extends StringConverter<ModuleFactory.BetaFactory> {
    @Override
    public String toString(ModuleFactory.BetaFactory object) {
        return object.getName();
    }

    @Override
    public ModuleFactory.BetaFactory fromString(String string) {
        return null;
    }
}
