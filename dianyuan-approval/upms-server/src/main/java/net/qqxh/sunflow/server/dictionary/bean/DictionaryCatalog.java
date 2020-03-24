package net.qqxh.sunflow.server.dictionary.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 数据字典目录
 *
 * @author cjy
 * @fileName DictionaryCatalog.java
 * @date 2019/5/25 15:34
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("supms_dictionary_catalog")
public class DictionaryCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableId(type = IdType.INPUT)
    private String catalogCode;

    /**
     * 字典名称
     */
    private String catalogName;

    /**
     * 字典描述
     */
    private String catalogDesc;

    /**
     * 字典类型(S-系统  U-用户)
     */
    private String catalogType;

    /**
     * 字典结构(T-树形   L-列表)
     */
    private String catalogStruct;

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