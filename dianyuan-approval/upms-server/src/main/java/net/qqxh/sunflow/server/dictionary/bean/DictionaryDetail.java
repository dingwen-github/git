package net.qqxh.sunflow.server.dictionary.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 数据字典明细
 *
 * @author cjy
 * @fileName DictionaryDetail.java
 * @date 2019/5/25 15:54
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("supms_dictionary_detail")
public class DictionaryDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableId(type = IdType.INPUT)
    private String catalogCode;

    /**
     * 数据编码
     */
    @TableId(type = IdType.INPUT)
    private String dataCode;

    /**
     * 数据值
     */
    private String dataValue;

    /**
     * 父级数据编码
     */
    private String dataParent;

    /**
     * 扩展值
     */
    private String dataValue2;

    /**
     * 数据描述
     */
    private String dataDesc;

    /**
     * 排序号
     */
    private Integer dataOrder;

    @TableField(exist = false)
    List<DictionaryDetail> children;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
}