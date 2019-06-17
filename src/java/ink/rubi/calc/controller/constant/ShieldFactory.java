package ink.rubi.calc.controller.constant;

import lombok.Getter;

/**
 * 单位 ：‱
 *
 * @author Henry Wang
 * @since 2019-06-17 13:37
 */
@Getter
public enum ShieldFactory {
    NONE("None", 0, 0, 0,0),
    TEDIORE("Tediore", -1200, 1200, -1600,-225),
    BANDIT("Bandit", 300, 2400, 800,1350),
    VLADOF("Vladof", -300, 900, 400,0),
    DAHL("Dahl", -600, -300, -2800,225),
    ANSHIN("Anshin", 1500, -2400, 2800,-450),
    MALIWAN("Maliwan", -1500, 300, 0,-1800  ),
    TORGUE("Torgue", 900, 600, 800,1125),
    HYPERION("Hyperion", -1200, -600, -3200,-675),
    PANGOLIN("Pangolin", 2400, -2100, 3200,675);


    String name;
    /**
     * 盾容增幅
     */
    int capacity;
    /**
     * 充能速率增幅
     */
    int speed;
    /**
     * 充能延迟增幅
     */
    int delay;

    /**
     * 血上限降低增幅
     */
    int maxHpDecrease;

    ShieldFactory(String name, int capacity, int speed, int delay, int maxHpDecrease) {
        this.name = name;
        this.capacity = capacity;
        this.speed = speed;
        this.delay = delay;
        this.maxHpDecrease = maxHpDecrease;
    }
}
