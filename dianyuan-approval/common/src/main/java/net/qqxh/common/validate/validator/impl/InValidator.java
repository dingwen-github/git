package net.qqxh.common.validate.validator.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.validator.Validator;
import org.apache.commons.lang3.StringUtils;


/**
 * Copyright (C), 2002-2019,第七班
 * 〈包含校验，判断字符串是否包含在配置项中〉<br>
 * 〈例如：@Validate(rules = "type=in:{reg:'a,b,c,d,e,f',msg:'非法类型'};") reg为所有项配置,msg为校验失败提醒〉
 *
 * @author jason
 * @fileName: NumberValidator.java
 * @date: 2019/6/26 17:29
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InValidator implements Validator {
    @Override
    public boolean match(String val, JSONObject validateRule) {
        if (StringUtils.isBlank(val)) {
            return false;
        }
        String setting = validateRule.getString("reg");
        if(StringUtils.isNotEmpty(setting)){
            String []arr=setting.split(",");
            for(String s:arr){
                if(StringUtils.equals(s,val)){
                    return true;
                }
            }
        }
        return false;
    }

}
