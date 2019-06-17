package ink.rubi.calc.controller.constant;

import lombok.Getter;

/**
 * 单位： ‱
 * @author Henry Wang
 * @since 2019-06-17 15:05
 */
public class ModuleFactory {
    @Getter
    public enum BetaFactory {
        NONE("None", 0),
        A0_B0_C0("A0_B0_C0", -1200),
        A1_B0_C0("A1_B0_C0", -1),
        A2_B0_C0("A2_B0_C0", -12),
        A3_B0_C0("A3_B0_C0", -12),
        A4_B0_C0("A4_B0_C0", -12),
        A5_B0_C0("A5_B0_C0", 24);
        String name;
        /**
         * 最大生命值增幅
         */
        int maxHpIncrease;


        BetaFactory(String name, int maxHpIncrease) {
            this.name = name;
            this.maxHpIncrease = maxHpIncrease;
        }
    }

    @Getter
    public enum GammaFactory {
        NONE("None", 0),
        A0_B0_C0("A0_B0_C0", -12),
        A0_B1_C0("A0_B1_C0", -12),
        A0_B2_C0("A0_B2_C0", -12),
        A0_B3_C0("A0_B3_C0", -12),
        A0_B4_C0("A0_B4_C0", -12),
        A0_B5_C0("A0_B5_C0", 24);
        String name;
        /**
         * 恢复速度增幅
         */
        int recovery;


        GammaFactory(String name, int recovery) {
            this.name = name;
            this.recovery = recovery;
        }
    }
}
