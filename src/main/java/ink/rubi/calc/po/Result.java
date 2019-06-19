package ink.rubi.calc.po;

import lombok.Data;

/**
 * @author Rubi
 * @since 2019-06-17 15:49
 */
@Data
public class Result {
    /*护盾部分*/
    /*
     * 盾容
     */
    long capacityVal;
    /**
     * 充能速率
     */
    long speedVal;
    /**
     * 充能延迟
     */
    long delayVal;

    /**
     * 血上限降低
     */
    long maxHpDecreaseVal;

    /*模组部分*/
    /**
     * 最大生命值增幅
     */
    long maxHpIncrease;
    /**
     * 恢复速度增幅
     */
    long recovery;


}
