package ink.rubi.calc.controller.coventer;

import ink.rubi.calc.controller.constant.ModuleFactory;
import javafx.util.StringConverter;

/**
 * @author Rubi
 * @since 2019-06-17 15:22
 */
public class ModuleGammaFactoryItemConverter extends StringConverter<ModuleFactory.GammaFactory> {


    @Override
    public String toString(ModuleFactory.GammaFactory object) {
        return object.getName();
    }

    @Override
    public ModuleFactory.GammaFactory fromString(String string) {
        return null;
    }


}
