package com.example.paypalspringbootdemo.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author hsl
 * @since 2023-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_smp_agent_info")
@Getter
@Setter
public class SmpAgentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String agentCode;

    private String agentName;

    private String accessKey;

    private String secretKey;

    private String clientMacAddress;

    /**
     * 0:失效
            1:生效
     */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private String remark;

    private String parentAgentCode;

    /**
     * 云存储分成百分比
     */
    private BigDecimal percentageCs;

    /**
     * 流量卡分成百分比
     */
    private BigDecimal percentageFc;

    /**
     * 差价模式（0：不启用，1启用）
     */
    private Integer priceDifferenceMethod;

    private String oaDepartment;

    @TableField(exist = false)
    private Integer grade;


}
