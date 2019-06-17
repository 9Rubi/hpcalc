package ink.rubi.calc.test;

import ink.rubi.calc.controller.constant.ModuleFactory;
import ink.rubi.calc.controller.constant.Reality;
import ink.rubi.calc.controller.constant.ShieldFactory;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Rubi
 * @version : 2019-06-18 01:12 上午
 */
@Slf4j
public class GetAll {
    static BigDecimal staticVar = new BigDecimal(10000);
    static BigDecimal perLevel = new BigDecimal(1.13);
    static BigDecimal speedPower = new BigDecimal(10.24);
    static BigDecimal delayPower = new BigDecimal(3.5);
    static BigDecimal maxHpDecreasePower = new BigDecimal(32);
    static BigDecimal capacityPower = new BigDecimal(64);
    static BigDecimal maxHpIncreasePower = new BigDecimal(16);
    static BigDecimal recoveryPower = new BigDecimal(0.5);
    static int thereIs = 0;

    public static void main(String[] args) {
        show();
    }

    private static void show() {
        ShieldFactory[] shieldFactories = ShieldFactory.values();
        Reality[] realities = Reality.values();

        ModuleFactory.BetaFactory[] betaFactories = ModuleFactory.BetaFactory.values();
        ModuleFactory.GammaFactory[] gammaFactories = ModuleFactory.GammaFactory.values();
        int shieldLevel = 90;
        int count = 0;
        for (int moduleLevel = 90; moduleLevel >= 1; moduleLevel--) {
            log.info("{}", count);
            for (ModuleFactory.BetaFactory betaFactory : betaFactories) {
                loop:
                for (ModuleFactory.GammaFactory gammaFactory : gammaFactories) {
                    List<List<ShieldFactory>> lists = new ArrayList<>();
                    for (ShieldFactory alpha : shieldFactories) {
                        for (ShieldFactory beta : shieldFactories) {
                            for (ShieldFactory gamma : shieldFactories) {
                                for (List<ShieldFactory> list : lists) {
                                    if (list.contains(alpha) && list.contains(beta) && list.contains(gamma)) {
                                        continue loop;
                                    }
                                }
                                lists.add(new ArrayList<ShieldFactory>() {{
                                    add(alpha);
                                    add(beta);
                                    add(gamma);
                                }});
                                for (Reality reality : realities) {
                                    count++;
                                    doForEach(shieldLevel, moduleLevel, alpha, beta, gamma, reality, betaFactory, gammaFactory);
                                }
                            }
                        }
                    }
                }
            }
        }
        log.info("总共有{}种", thereIs);
    }

    private static void doForEach(int shieldLevel, int moduleLevel,
                                  ShieldFactory alpha, ShieldFactory beta, ShieldFactory gamma,
                                  Reality reality, ModuleFactory.BetaFactory betaFactory,
                                  ModuleFactory.GammaFactory gammaFactory) {
        int rateMaxHp = alpha.getMaxHpDecrease() +
                beta.getMaxHpDecrease() +
                gamma.getMaxHpDecrease() +
                reality.getMaxHpDecrease();
        BigDecimal maxHpDecreaseResult = new BigDecimal(rateMaxHp)
                .divide(staticVar).add(BigDecimal.ONE)
                .multiply(maxHpDecreasePower)
                .multiply(perLevel.pow(shieldLevel))
                .setScale(0, RoundingMode.HALF_UP);
        BigDecimal maxHpIncreaseResult = new BigDecimal(betaFactory.getMaxHpIncrease()).divide(staticVar).add(BigDecimal.ONE)
                .multiply(maxHpIncreasePower)
                .multiply(perLevel.pow(moduleLevel))
                .setScale(0, RoundingMode.HALF_UP);
        BigDecimal result = new BigDecimal(1410475)
                .subtract(maxHpDecreaseResult)
                .multiply(new BigDecimal(1.15))
                .add(maxHpIncreaseResult);
        int intValue = result.intValue();
        if (4 <= intValue && intValue < 500) {
            log.info("==========================================");
            log.info("护盾等级：" + shieldLevel);
            log.info("配件：" + alpha + "|" + beta + "|" + gamma);
            log.info("稀有度：+" + reality.getName());
            log.info("模组等级为：" + moduleLevel);
            log.info("配件：" + betaFactory + "|" + gammaFactory);
            log.info("最终血量:{}", intValue);
            log.info("==========================================");
            thereIs++;
        }

    }

}
