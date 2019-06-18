package ink.rubi.calc.controller.constant;

import lombok.Getter;

/**
 * 单位 ：‱
 *
 * @author Henry Wang
 * @since 2019-06-17 13:51
 */
@Getter
public enum Reality {
    WHITE("白色", 0, 0, 0,0),
    GREEN("绿色", 1200, 0, 0,-1350),
    BLUE("蓝色", 2400, 0, 0,-2700),
    PURPLE("紫色", 3600, 0, 0,-4050);


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

    Reality(String name, int capacity, int speed, int delay,int maxHpDecrease) {
        this.name = name;
        this.capacity = capacity;
        this.speed = speed;
        this.delay = delay;
        this.maxHpDecrease = maxHpDecrease;
    }
}
