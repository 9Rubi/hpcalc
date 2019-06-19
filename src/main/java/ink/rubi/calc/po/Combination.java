package ink.rubi.calc.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2019-06-19
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Combination implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("shield_level")
    private Integer shieldLevel;

    @TableField("beta")
    private String beta;

    @TableField("alpha")
    private String alpha;

    @TableField("gamma")
    private String gamma;

    @TableField("reality")
    private String reality;

    @TableField("module_level")
    private Integer moduleLevel;

    @TableField("module_beta")
    private String moduleBeta;

    @TableField("max_hp_decrease")
    private BigDecimal maxHpDecrease;

    @TableField("max_hp_increase")
    private BigDecimal maxHpIncrease;


}
