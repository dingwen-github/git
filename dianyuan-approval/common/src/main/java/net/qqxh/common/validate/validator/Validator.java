package net.qqxh.common.validate.validator;

import com.alibaba.fastjson.JSONObject;

public interface Validator {
    boolean match(String val, JSONObject validateRule);
}
