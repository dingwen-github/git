package net.qqxh.common.validate.model.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.common.validate.core.Rule;
import net.qqxh.common.validate.enums.Constants;
import net.qqxh.common.validate.enums.Model;
import net.qqxh.common.validate.model.ValidateModel;
import net.qqxh.common.validate.util.AspectUtil;
import net.qqxh.common.validate.util.ValidateUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MvcDeliverModel implements ValidateModel {
    @Override
    public String getParamVal(ProceedingJoinPoint joinPoint, String paramName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getParameter(paramName);
    }

    @Override
    public String getDefauleMsgTemplete() {
        return Constants.DEFAULT_TEMPLETE;
    }

    @Override
    public boolean supportsModel(ProceedingJoinPoint joinPoint) {
        try {
            Boolean b1 = AspectUtil.calssHasAnnotation(joinPoint, Controller.class);
            Boolean b2 = AspectUtil.methodHasAnnotation(joinPoint, ResponseBody.class);
            /*有控制器*/
            return b1 && !b2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supportsModel(String modelType) {
        return StringUtils.equals(modelType, Model.MVC_HTML);
    }

    @Override
    public JSONObject buildErrMsg(Rule rule, String val, String msgTemp) {
        JSONObject jsonObject = ValidateUtil.buildFailMsg(rule, val, msgTemp);
        ValidateUtil.setFailMsg(jsonObject);
        return jsonObject;
    }

    @Override
    public String msgType() {
        return Constants.DELIVER;
    }
}