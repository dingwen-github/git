package net.qqxh.common.validate.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.core.Rule;
import net.qqxh.common.validate.enums.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class ValidateUtil {
    private static ThreadLocal<JSONObject> mThreadLocal = new ThreadLocal<JSONObject>();

    private ValidateUtil() {
    }

    public static Map<String, Rule> parseRules(String rulesStr) {
        Map<String, Rule> map = new IdentityHashMap();
        if (StringUtils.isNotBlank(rulesStr)) {
            String[] paraRules = rulesStr.split(";");
            for (String paramRule : paraRules) {
                Rule rule = ValidateUtil.buildRule(paramRule);
                map.put(rule.getParamName(), rule);
            }
        }
        return map;
    }

    /**
     * 解析注解配置项
     *
     * @param ruleStr
     * @return
     */
    public static Rule buildRule(String ruleStr) {
        String[] params = ruleStr.split("=");
        Rule rule = new Rule();
        rule.setParamName(params[0]);
        String rulesStr = "{" + params[1] + "}";
        JSONObject jsonObject = JSON.parseObject(rulesStr);
        Set<String> rules = jsonObject.keySet();
        for (String type : rules) {
            rule.setValidateType(type);
            rule.setValidateRule(jsonObject.getJSONObject(type));
        }
        return rule;
    }

    public static JSONObject buildFailMsg(Rule rule, String val, String msgTemp) {
        String msg = rule.getValidateRule().getString("msg");
        String code = rule.getValidateRule().getString("code");
        String paramName = rule.getParamName();
        String[] searchList = {Constants.CODE_REPLACE, Constants.MSG_REPLACE, Constants.PARAM_REPLACE, Constants.VAL_REPLACE};
        String[] paramList = {code, msg, paramName, val == null ? "" : val};
        String result = StringUtils.replaceEach(msgTemp, searchList, paramList);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }

    /**
     * 功能描述: <br>
     * 〈校验请求是否通过〉
     *
     * @return boolean
     * @throws
     * @Author jason
     * @date 2019/6/27 15:57
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean check() {
        Object object = mThreadLocal.get();
        return object == null;
    }

    /**
     * 功能描述: <br>
     * 〈获取校验失败描述信息〉
     *
     * @return java.lang.Object
     * @throws
     * @Author jason
     * @date 2019/6/27 15:58
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */

    public static Object getFailMsg() {
        return mThreadLocal.get();
    }

    /**
     * 功能描述: <br>
     * 〈获取校验失败描述信息〉
     *
     * @return java.lang.Object
     * @throws
     * @Author jason
     * @date 2019/6/27 15:58
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */

    public static void setFailMsg(JSONObject jsonObject) {
        mThreadLocal.set(jsonObject);
    }
}
