package net.qqxh.common.validate.helper.impl;


import net.qqxh.common.validate.enums.ValidatorType;
import net.qqxh.common.validate.helper.ValidateHelper;

public class NumberValidateHelper extends ValidateHelper {

    @Override
    public NumberValidateHelper msg(String msg) {
        this.rule.put("msg", msg);
        return this;
    }

    @Override
    public ValidateHelper code(String code) {
        this.rule.put("code", code);
        return this;
    }

    @Override
    public NumberValidateHelper param(String param) {
        this.param = ValidatorType.INT + "=" + param;
        return this;
    }

    public NumberValidateHelper min(int min) {
        this.rule.put("min", min);
        return this;
    }

    public NumberValidateHelper max(int max) {
        this.rule.put("max", max);
        return this;
    }


}
