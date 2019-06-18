//package ink.rubi.calc.test;
//
//import ink.rubi.calc.controller.constant.ModuleFactory;
//import ink.rubi.calc.controller.constant.Reality;
//import ink.rubi.calc.controller.constant.ShieldFactory;
//import lombok.extern.slf4j.Slf4j;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * @author : Rubi
// * @version : 2019-06-18 01:12 上午
// */
//@Slf4j
//public class GetAll {
//    private static final BigDecimal staticVar = BigDecimal.valueOf(10000L);
//    private static final BigDecimal perLevel = BigDecimal.valueOf(1.13);
//    private static final BigDecimal maxHpDecreasePower = BigDecimal.valueOf(32L);
//    private static final BigDecimal maxHpIncreasePower = BigDecimal.valueOf(16L);
//    private static int thereIs = 0;
//    private static int totalIs = 0;
//    private static int hpMax = 1410475;
//    private static double bonus = 1.15;
//
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入当前血量，默认为1410475");
//        String hpMaxStr = scanner.nextLine();
//        System.out.println("请输入技能增幅，默认为1.15（15%）");
//        String bonusStr = scanner.nextLine();
//        if (!hpMaxStr.isEmpty()) {
//            try {
//                hpMax = Integer.parseInt(hpMaxStr);
//            } catch (NumberFormatException e) {
//            }
//        }
//        if (!bonusStr.isEmpty()) {
//            try {
//                bonus = Double.parseDouble(bonusStr);
//            } catch (NumberFormatException e) {
//            }
//        }
//        show();
//    }
//
//    private static void show() {
//        log.info("======================================================================");
//        ShieldFactory[] shieldFactories = ShieldFactory.values();
//        Reality[] realities = Reality.values();
//        ModuleFactory.BetaFactory[] betaFactories = ModuleFactory.BetaFactory.values();
//        for (int shieldLevel = 90; shieldLevel >= 1; shieldLevel--) {
//            for (int moduleLevel = 90; moduleLevel >= 1; moduleLevel--) {
//                loop:
//                for (ModuleFactory.BetaFactory betaFactory : betaFactories) {
//                    List<List<ShieldFactory>> lists = new ArrayList<>();
//                    for (ShieldFactory alpha : shieldFactories) {
//                        for (ShieldFactory beta : shieldFactories) {
//                            for (ShieldFactory gamma : shieldFactories) {
//                                for (List<ShieldFactory> list : lists) {
//                                    if (list.contains(alpha) && list.contains(beta) && list.contains(gamma)) {
//                                        continue loop;
//                                    }
//                                }
//                                lists.add(new ArrayList<ShieldFactory>() {{
//                                    add(alpha);
//                                    add(beta);
//                                    add(gamma);
//                                }});
//                                for (Reality reality : realities) {
//                                    doForEach(shieldLevel, moduleLevel, alpha, beta, gamma, reality, betaFactory, hpMax, bonus);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        log.info("======================================================================");
//        log.info("总共有{}种合适(最终血量4~1000)的组合", thereIs);
//        log.info("总共遍历了{}种", totalIs);
//    }
//
//    private static void doForEach(final int shieldLevel, final int moduleLevel,
//                                  final ShieldFactory alpha, final ShieldFactory beta, final ShieldFactory gamma,
//                                  final Reality reality, final ModuleFactory.BetaFactory betaFactory,
//                                  final int hpMax, final double bonus) {
//        int rateMaxHp = alpha.getMaxHpDecrease() +
//                beta.getMaxHpDecrease() +
//                gamma.getMaxHpDecrease() +
//                reality.getMaxHpDecrease();
//        BigDecimal maxHpDecreaseResult = new BigDecimal(rateMaxHp)
//                .divide(staticVar).add(BigDecimal.ONE)
//                .multiply(maxHpDecreasePower)
//                .multiply(perLevel.pow(shieldLevel))
//                .setScale(0, RoundingMode.HALF_UP);
//        BigDecimal maxHpIncreaseResult = new BigDecimal(betaFactory.getMaxHpIncrease())
//                .divide(staticVar).add(BigDecimal.ONE)
//                .multiply(maxHpIncreasePower)
//                .multiply(perLevel.pow(moduleLevel))
//                .setScale(0, RoundingMode.HALF_UP);
//        BigDecimal result = new BigDecimal(hpMax)
//                .subtract(maxHpDecreaseResult)
//                .multiply(new BigDecimal(bonus))
//                .add(maxHpIncreaseResult);
//        int intValue = result.intValue();
//        if (4 <= intValue && intValue <= 1000) {
//            log.info("==========================================");
//            log.info("护盾等级：{},配件：{}|{}|{},稀有度：{}.....模组等级为：{},配件：{}",
//                    shieldLevel, alpha, beta, gamma, reality.getName(), moduleLevel, betaFactory);
//            log.info("最终血量：{}", intValue);
//            log.info("==========================================");
//            thereIs++;
//        }
//        totalIs++;
//    }
//
//}
