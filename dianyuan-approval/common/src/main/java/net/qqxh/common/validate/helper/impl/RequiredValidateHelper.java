package net.qqxh.common.validate.helper.impl;


import net.qqxh.common.validate.enums.ValidatorType;
import net.qqxh.common.validate.helper.ValidateHelper;

public class RequiredValidateHelper extends ValidateHelper {

    @Override
    public RequiredValidateHelper msg(String msg) {
        this.rule.put("msg", msg);
        return this;
    }

    @Override
    public ValidateHelper code(String code) {
        this.rule.put("code", code);
        return this;
    }

    @Override
    public RequiredValidateHelper param(String param) {
        this.param = ValidatorType.REQUIRED + "=" + param;
        return this;
    }

    public RequiredValidateHelper min(int min) {
        this.rule.put("min", min);
        return this;
    }

    public RequiredValidateHelper max(int max) {
        this.rule.put("max", max);
        return this;
    }


}
