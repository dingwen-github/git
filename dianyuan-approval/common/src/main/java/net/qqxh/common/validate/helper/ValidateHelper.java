package net.qqxh.common.validate.helper;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.helper.impl.NumberValidateHelper;
import net.qqxh.common.validate.helper.impl.RequiredValidateHelper;
import org.apache.commons.lang3.StringUtils;

public abstract class ValidateHelper {
    public String param;
    public JSONObject rule = new JSONObject();

    public abstract ValidateHelper msg(String msg);

    public abstract ValidateHelper code(String code);

    public abstract ValidateHelper param(String param);

    public String next(String rule) {
        String end = end();
        if (StringUtils.isNotBlank(rule)) {
            return end + rule;
        } else {
            return end;
        }
    }

    public static RequiredValidateHelper required() {
        return new RequiredValidateHelper();
    }

    public static NumberValidateHelper number() {
        return new NumberValidateHelper();
    }

    public String end() {
        return this.param + ":" + this.rule.toString() + ";";
    }
}
