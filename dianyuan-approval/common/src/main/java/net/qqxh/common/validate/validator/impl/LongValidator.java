package net.qqxh.common.validate.validator.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.validator.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * Copyright (C), 2002-2019,第七班
 * 〈数字校验〉<br>
 * 〈例如：@Validate(rules = "count=long:{max:1,min:20,msg:'请输入1-20之间的数字'};") max为最大值 min为最小值msg为校验失败提醒〉
 *
 * @author jason
 * @fileName: NumberValidator.java
 * @date: 2019/6/26 17:29
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class LongValidator implements Validator {
    @Override
    public boolean match(String val, JSONObject validateRule) {
        if (StringUtils.isBlank(val)) {
            return false;
        }
        try {
            long min = validateRule.getLongValue("min");
            long max = validateRule.getLongValue("max");
            long temp = Long.parseLong(val.trim());
            if (temp < min || temp > max) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
