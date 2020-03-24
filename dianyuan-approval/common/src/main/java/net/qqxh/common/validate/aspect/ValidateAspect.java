package net.qqxh.common.validate.aspect;

import com.google.common.collect.Maps;
import net.qqxh.common.validate.annotation.Validate;
import net.qqxh.common.validate.core.DynamicServer;
import net.qqxh.common.validate.core.Rule;
import net.qqxh.common.validate.enums.Constants;
import net.qqxh.common.validate.model.ModelManger;
import net.qqxh.common.validate.model.ValidateModel;
import net.qqxh.common.validate.model.manager.DefaultModelManger;
import net.qqxh.common.validate.util.ValidateUtil;
import net.qqxh.common.validate.validator.ValidatorManager;
import net.qqxh.common.validate.validator.manager.DefaultValidatorManager;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Copyright (C), 2002-2019,第七班
 * 〈注解式校验切面类〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: ValidateAspect.java
 * @date: 2019/6/28 12:54
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Aspect
public class ValidateAspect {
    public final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private DynamicServer dynamicServer;
    private ModelManger modelManger = new DefaultModelManger();
    private ValidatorManager validatorManager = new DefaultValidatorManager();

    public ValidateAspect() {
        LOG.info("Welcome to the straw hat group validate,init success");
    }

    @Pointcut("@annotation(net.qqxh.common.validate.annotation.Validate)")
    public void validatePointcut() {
    }

    @Autowired(required = false)
    public void setDynamicServer(DynamicServer dynamicServer) {
        this.dynamicServer = dynamicServer;
    }

    @Autowired(required = false)
    public void setModelManger(ModelManger modelManger) {
        this.modelManger = modelManger;
    }

    @Autowired(required = false)
    public void setValidatorManager(ValidatorManager validatorManager) {
        this.validatorManager = validatorManager;
    }

    public ValidatorManager getValidatorManager() {
        return validatorManager;
    }

    public ModelManger getModelManger() {
        return modelManger;
    }

    @Around("validatePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        /*获取注解*/
        Validate validate = getAnnotation(joinPoint);
        /*对校验规则进行装箱*/
        Map<String, Rule> rules = packingRule(validate);
        /*校验模式*/
        String model = validate.model();
        String msgTemp = validate.msgTemp();
        String info = joinPoint.getTarget().getClass() + "." + joinPoint.getSignature().getName() + "()";
        LOG.info("start validate joinPoint :{}, rules :{}", info, rules.toString());
        if (!rules.isEmpty()) {
            ValidateModel validateModel = modelManger.obtainModel(model, joinPoint);
            for (String paramName : rules.keySet()) {
                Rule rule = rules.get(paramName);
                /*获取参数*/
                String val = validateModel.getParamVal(joinPoint, paramName);
                /*进行校验*/
                boolean matchResult = validatorManager.match(val, rule);
                if (!matchResult) {
                    String temp = StringUtils.isEmpty(msgTemp) ? validateModel.getDefauleMsgTemplete() : msgTemp;
                    Object msg = validateModel.buildErrMsg(rule, val, temp);
                    LOG.info("validate failed ==> val:{},{}", val, rule);
                    if (!StringUtils.equals(Constants.DELIVER, validateModel.msgType())) {
                        return msg;
                    }
                    break;
                }
            }
        }
        Object result = joinPoint.proceed();
        return result;
    }

    /**
     * 在指定切入点发生异常时，打印异常日志
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(pointcut = "validatePointcut()", throwing = "ex")
    public void afterThrowsAdvice(JoinPoint joinPoint, Exception ex) {
        String errorInfo = joinPoint.getTarget().getClass() + "." + joinPoint.getSignature().getName() + "()" + "发生异常";
        LOG.error(errorInfo);
        if (LOG.isDebugEnabled()) {
            // lw 打印错误堆栈
            StackTraceElement[] st = ex.getStackTrace();
            for (int i = 0; i < st.length; i++) {
                LOG.error(i == 0 ? st[i].toString() : "            at " + st[i].toString());
            }
            LOG.error(ex.getLocalizedMessage());
        }
    }

    /**
     * 获取方法中的注解信息
     */
    private Validate getAnnotation(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Validate.class);
        }
        return null;
    }

    /**
     * 获取配置的校验规则列表
     * 已经添加scm支撑
     *
     * @param validate
     * @return
     */
    private Map<String, Rule> packingRule(Validate validate) {
        if (validate == null) {
            return Maps.newHashMap();
        }
        String rules = validate.rules();
        Map<String, Rule> map = ValidateUtil.parseRules(rules);
        //支持不用配置所有参数项，只覆盖掉配置
        String dynamicKey = validate.dynamicKey();
        if (StringUtils.isNotBlank(dynamicKey) && dynamicServer != null) {
            String dynamicRules = dynamicServer.getValue(dynamicKey, rules);
            map.putAll(ValidateUtil.parseRules(dynamicRules));
        }
        return map;
    }


}
