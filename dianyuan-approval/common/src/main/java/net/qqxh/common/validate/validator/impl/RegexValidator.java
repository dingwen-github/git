package net.qqxh.common.validate.validator.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Copyright (C), 2002-2019,第七班
 * 〈数字校验〉<br>
 * 〈例如：@Validate(rules = "paramname=regex:{reg:'正则表达式',msg:'入参格式不正确'};") reg正则表达式,msg为校验失败提醒〉
 * <p>
 * ValidateRegex中有常用的正则表达式。后期还需要扩展，可用性未做测试，使用时请自行测试后使用
 *
 * @author jason
 * @fileName: NumberValidator.java
 * @date: 2019/6/26 17:29
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RegexValidator implements Validator {
    @Override
    public boolean match(String val, JSONObject validateRule) {
        if (StringUtils.isBlank(val)) {
            return false;
        }
        String reg = validateRule.getString("reg");
        Pattern pattern = Pattern.compile(reg);
        /*Pattern.compile(reg, Pattern.CASE_INSENSITIVE);忽略大小写校验*/
        Matcher matcher = pattern.matcher(val);
        return matcher.matches();
    }

}
