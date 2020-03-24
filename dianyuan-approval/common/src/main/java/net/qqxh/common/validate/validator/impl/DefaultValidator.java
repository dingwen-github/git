package net.qqxh.common.validate.validator.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.validator.Validator;

/**
 * Copyright (C), 2002-2019,第七班
 * 〈一句话功能简述〉<br>
 * 〈require:{max:1,min:2}〉
 *
 * @author jason
 * @fileName: RequireValidator.java
 * @date: 2019/6/26 17:29
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DefaultValidator implements Validator {
    @Override
    public boolean match(String val, JSONObject validateRule) {
        return true;
    }


}
