package ink.rubi.calc.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : Rubi
 * @version : 2019-05-19 01:50 上午
 */
@Slf4j
public class GUIConfig {
/*    @Getter
    private static boolean showLogInGUIWindow = false;
    @Getter
    private static boolean logConfigInfoWhenGenerateCode = false;*/

    public static void init(){
        Properties prop = new Properties();
        try (InputStream inputStream = GUIConfig.class.getResourceAsStream("/conf.properties")) {
            prop.load(inputStream);
        } catch (IOException e) {
            log.error("加载配置文件异常:{}", e.getMessage(), e);
        }
/*        showLogInGUIWindow = Boolean.valueOf(prop.getProperty("showLogInGUIWindow", "false"));
        logConfigInfoWhenGenerateCode = Boolean.valueOf(prop.getProperty("logConfigInfoWhenGenerateCode", "false"));*/
    }
}
