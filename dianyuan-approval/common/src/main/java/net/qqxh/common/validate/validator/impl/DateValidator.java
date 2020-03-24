package net.qqxh.common.validate.validator.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 * Copyright (C), 2002-2019,第七班
 * 〈数字校验〉<br>
 * 〈例如：@Validate(rules = "date=date:{fmt:'yyyyMMdd',msg:'时间格式不正确',after:100,before:100};") fmt时间格式,msg为校验失败提醒〉
 *
 * @author jason
 * @fileName: NumberValidator.java
 * @date: 2019/6/26 17:29
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DateValidator implements Validator {
    @Override
    public boolean match(String val, JSONObject validateRule) {
        if (StringUtils.isBlank(val)) {
            return false;
        }

        try {
            String fmt = validateRule.getString("fmt");
            Long after = validateRule.getLong("after");
            Long before = validateRule.getLong("before");
            /*校验格式是否满足*/
            DateTimeFormatter formatter = DateTimeFormat.forPattern(fmt);
            DateTime valtime= DateTime.parse(val, formatter);
            if (after != null) {
                DateTime time = new DateTime().plus(after);
                if(!time.isAfter(valtime)){
                    return false;
                };
            }
            if (before != null) {
                DateTime time = new DateTime().plus(-before);
                if(!time.isBefore(valtime)){
                    return false;
                };
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
