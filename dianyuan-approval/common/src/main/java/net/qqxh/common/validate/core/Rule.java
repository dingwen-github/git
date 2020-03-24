package net.qqxh.common.validate.core;

import com.alibaba.fastjson.JSONObject;

public class Rule {
    /**
     * 校验参数名称
     */
    String paramName;
    /**
     * 校验类型
     */
    String validateType;
    /**
     * 校验逻辑以json存储
     */
    JSONObject validateRule;


    public Rule() {
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getValidateType() {
        return validateType;
    }

    public void setValidateType(String validateType) {
        this.validateType = validateType;
    }

    public JSONObject getValidateRule() {
        return validateRule;
    }

    public void setValidateRule(JSONObject validateRule) {
        this.validateRule = validateRule;
    }


    @Override
    public String toString() {
        return "paramName:" + this.paramName + ",validateType:" + this.validateType + ",validateRule-->" + this.validateRule.toString();
    }
}
