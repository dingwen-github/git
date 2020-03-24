package net.qqxh.common.validate.model.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.core.Rule;
import net.qqxh.common.validate.enums.Constants;
import net.qqxh.common.validate.enums.Model;
import net.qqxh.common.validate.model.ValidateModel;
import net.qqxh.common.validate.util.AspectUtil;
import net.qqxh.common.validate.util.ValidateUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Copyright (C), 2002-2019,第七班
 * 〈参数列表校验，目前只支持字符串类型〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: ArgsModel.java
 * @date: 2019/6/29 9:31
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ArgsModel implements ValidateModel {
    @Override
    public String getParamVal(ProceedingJoinPoint joinPoint, String paramName) {
        return (String) AspectUtil.getArgValue(joinPoint, paramName);
    }

    @Override
    public String getDefauleMsgTemplete() {
        return Constants.DEFAULT_TEMPLETE;
    }

    /**
     * 此种类型不支持自动探查需要手动指定
     *
     * @param joinPoint
     * @return
     */
    @Deprecated
    @Override
    public boolean supportsModel(ProceedingJoinPoint joinPoint) {
        return false;
    }

    @Override
    public boolean supportsModel(String modelType) {
        return StringUtils.equals(modelType, Model.ARGS);
    }

    @Override
    public JSONObject buildErrMsg(Rule rule, String val, String msgTemp) {
        return ValidateUtil.buildFailMsg(rule, val, msgTemp);
    }

    @Override
    public String msgType() {
        return Constants.RETURN;
    }
}