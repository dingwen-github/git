package net.qqxh.common.validate.validator.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.validator.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * Copyright (C), 2002-2019,第七班
 * 〈非空校验〉<br>
 * 〈例如：@Validate(rules = "loginName=required:{max:1,min:2,msg:'请输入用户名'};") max为最大长度 min为最小长度msg为校验失败提醒〉
 *
 * @author jason
 * @fileName: RequireValidator.java
 * @date: 2019/6/26 17:29
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RequireValidator implements Validator {
    @Override
    public boolean match(String val, JSONObject validateRule) {
        if (StringUtils.isBlank(val)) {
            return false;
        }
        Integer min = validateRule.getInteger("min");
        Integer max = validateRule.getInteger("max");
        if (min != null && val.length() < min) {
            return false;
        }
        if (max != null && val.length() > max) {
            return false;
        }
        return true;
    }
}
