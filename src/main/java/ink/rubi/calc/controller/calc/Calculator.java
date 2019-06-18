package ink.rubi.calc.controller.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Henry Wang
 * @since 2019-06-18 11:47
 */
public class Calculator {

    private static final BigDecimal tenThousand = BigDecimal.valueOf(10000L);
    private static final BigDecimal hundred = BigDecimal.valueOf(100L);
    private static final BigDecimal perLevel = BigDecimal.valueOf(1.13);
    private static final BigDecimal speedPower = BigDecimal.valueOf(10.24);
    private static final BigDecimal delayPower = BigDecimal.valueOf(3.5);
    private static final BigDecimal maxHpDecreasePower = BigDecimal.valueOf(32L);
    private static final BigDecimal capacityPower = BigDecimal.valueOf(64L);
    private static final BigDecimal maxHpIncreasePower = BigDecimal.valueOf(16L);
    private static final BigDecimal recoveryPower = BigDecimal.valueOf(0.5);

    public static BigDecimal calcMaxHpDecrease(final int rateMaxHp, final int shieldLevel) {
        return new BigDecimal(rateMaxHp)
                .divide(tenThousand).add(BigDecimal.ONE)
                .multiply(maxHpDecreasePower)
                .multiply(perLevel.pow(shieldLevel))
                .setScale(0, RoundingMode.HALF_UP);


    }

    public static BigDecimal calcCapacity(final int rateCapacity, final int shieldLevel) {
        return new BigDecimal(rateCapacity)
                .divide(tenThousand).add(BigDecimal.ONE)
                .multiply(capacityPower)
                .multiply(perLevel.pow(shieldLevel))
                .setScale(0, RoundingMode.HALF_UP);
    }

    public static BigDecimal calcSpeed(final int rateSpeed, final int shieldLevel) {
        BigDecimal speedResult = new BigDecimal(rateSpeed)
                .divide(tenThousand);

        if (speedResult.compareTo(BigDecimal.ZERO) >= 0) {
            speedResult = speedPower
                    .multiply(perLevel.pow(shieldLevel))
                    .multiply(speedResult.add(BigDecimal.ONE))
                    .setScale(0, RoundingMode.HALF_UP);
        } else {
            speedResult = speedPower
                    .multiply(perLevel.pow(shieldLevel))
                    .divide(speedResult.abs().add(BigDecimal.ONE), 0, RoundingMode.HALF_UP);
        }
        return speedResult;
    }


    public static BigDecimal calcDelay(final int rateDelay) {
        BigDecimal delayResult = new BigDecimal(rateDelay)
                .divide(tenThousand);
        if (delayResult.compareTo(BigDecimal.ZERO) >= 0) {
            delayResult = delayPower.multiply(delayResult.add(BigDecimal.ONE)).setScale(2, RoundingMode.HALF_UP);
        } else {
            delayResult = delayPower.divide(
                    delayResult.abs().add(BigDecimal.ONE), 2, RoundingMode.HALF_UP
            );
        }
        return delayResult;
    }

    public static BigDecimal calcMaxHpIncrease(final int rateMaxHpIncrease, final int moduleLevel) {
        return new BigDecimal(rateMaxHpIncrease).divide(hundred).add(BigDecimal.ONE)
                .multiply(maxHpIncreasePower)
                .multiply(perLevel.pow(moduleLevel))
                .setScale(0, RoundingMode.HALF_UP);
    }

    public static BigDecimal calcRecovery(final int rateRecovery, final int moduleLevel) {
        return new BigDecimal(rateRecovery).divide(hundred).add(BigDecimal.ONE)
                .multiply(recoveryPower)
                .multiply(perLevel.pow(moduleLevel))
                .setScale(0, RoundingMode.HALF_UP);
    }

    public static BigDecimal calcFinal(final BigDecimal hp, final BigDecimal maxHpDecrease,
                                       final BigDecimal bonus, final BigDecimal maxHpIncrease) {
        return hp.subtract(maxHpDecrease)
                .multiply(bonus)
                .add(maxHpIncrease);
    }


}
