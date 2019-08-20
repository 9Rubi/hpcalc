package ink.rubi.calc.controller.calc;

import ink.rubi.calc.controller.constant.ModuleFactory;
import ink.rubi.calc.controller.constant.Reality;
import ink.rubi.calc.controller.constant.ShieldFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Rubi
 * @since 2019-06-18 11:47
 */
@SuppressWarnings("all")
public class Calculator {
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);
    private static final BigDecimal TEN_THOUSAND = BigDecimal.valueOf(10000L);
    private static final BigDecimal PER_LEVEL = BigDecimal.valueOf(1.13);
    private static final BigDecimal SPEED_POWER = BigDecimal.valueOf(10.24);
    private static final BigDecimal DELAY_POWER = BigDecimal.valueOf(3.5);
    private static final BigDecimal MAX_HP_DECREASE_POWER = BigDecimal.valueOf(32L);
    private static final BigDecimal CAPACITY_POWER = BigDecimal.valueOf(64L);
    private static final BigDecimal MAX_HP_INCREASE_POWER = BigDecimal.valueOf(16L);
    private static final BigDecimal RECOVERY_POWER = BigDecimal.valueOf(0.5);
    public static final ShieldFactory[] SHIELD_FACTORIES = ShieldFactory.values();
    public static final Reality[] REALITIES = Reality.values();
    public static final ModuleFactory.BetaFactory[] BETA_FACTORIES = ModuleFactory.BetaFactory.values();
    public static BigDecimal calcMaxHpDecrease(final int rateMaxHp, final int shieldLevel) {
        return new BigDecimal(rateMaxHp)
                .divide(TEN_THOUSAND).add(BigDecimal.ONE)
                .multiply(MAX_HP_DECREASE_POWER)
                .multiply(PER_LEVEL.pow(shieldLevel))
                .setScale(0, RoundingMode.HALF_UP);


    }
    public static BigDecimal calcCapacity(final int rateCapacity, final int shieldLevel) {
        return new BigDecimal(rateCapacity)
                .divide(TEN_THOUSAND).add(BigDecimal.ONE)
                .multiply(CAPACITY_POWER)
                .multiply(PER_LEVEL.pow(shieldLevel))
                .setScale(0, RoundingMode.HALF_UP);
    }
    public static BigDecimal calcSpeed(final int rateSpeed, final int shieldLevel) {
        BigDecimal speedResult = new BigDecimal(rateSpeed)
                .divide(TEN_THOUSAND);

        if (speedResult.compareTo(BigDecimal.ZERO) >= 0) {
            speedResult = SPEED_POWER
                    .multiply(PER_LEVEL.pow(shieldLevel))
                    .multiply(speedResult.add(BigDecimal.ONE))
                    .setScale(0, RoundingMode.HALF_UP);
        } else {
            speedResult = SPEED_POWER
                    .multiply(PER_LEVEL.pow(shieldLevel))
                    .divide(speedResult.abs().add(BigDecimal.ONE), 0, RoundingMode.HALF_UP);
        }
        return speedResult;
    }
    public static BigDecimal calcDelay(final int rateDelay) {
        BigDecimal delayResult = new BigDecimal(rateDelay)
                .divide(TEN_THOUSAND);
        if (delayResult.compareTo(BigDecimal.ZERO) >= 0) {
            delayResult = DELAY_POWER.multiply(delayResult.add(BigDecimal.ONE)).setScale(2, RoundingMode.HALF_UP);
        } else {
            delayResult = DELAY_POWER.divide(
                    delayResult.abs().add(BigDecimal.ONE), 2, RoundingMode.HALF_UP
            );
        }
        return delayResult;
    }
    public static BigDecimal calcMaxHpIncrease(final int rateMaxHpIncrease, final int moduleLevel) {
        return new BigDecimal(rateMaxHpIncrease).divide(HUNDRED).add(BigDecimal.ONE)
                .multiply(MAX_HP_INCREASE_POWER)
                .multiply(PER_LEVEL.pow(moduleLevel))
                .setScale(0, RoundingMode.HALF_UP);
    }
    public static BigDecimal calcRecovery(final int rateRecovery, final int moduleLevel) {
        return new BigDecimal(rateRecovery).divide(HUNDRED).add(BigDecimal.ONE)
                .multiply(RECOVERY_POWER)
                .multiply(PER_LEVEL.pow(moduleLevel))
                .setScale(0, RoundingMode.HALF_UP);
    }
    public static BigDecimal calcFinal(final BigDecimal hp, final BigDecimal maxHpDecrease,
                                       final BigDecimal bonus, final BigDecimal maxHpIncrease) {
        return hp.subtract(maxHpDecrease)
                .multiply(bonus)
                .add(maxHpIncrease);
    }
}
