
package net.qqxh.common.validate.model;

import net.qqxh.common.validate.core.Rule;
import org.aspectj.lang.ProceedingJoinPoint;

public interface ValidateModel {
    /**
     * 功能描述: <br>
     * 〈根据参数名称获取参数值〉
     *
     * @param joinPoint
     * @param paramName
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/28 16:09
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String getParamVal(ProceedingJoinPoint joinPoint, String paramName);

    /**
     * 功能描述: <br>
     * 〈获取默认响应模板〉
     *
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/28 16:10
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String getDefauleMsgTemplete();

    /**
     * 功能描述: <br>
     * 〈分析入参分析模式是否支持〉
     *
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/28 16:10
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    boolean supportsModel(ProceedingJoinPoint joinPoint);

    /**
     * 功能描述: <br>
     * 〈判断入参模式是否支持〉
     *
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/28 16:10
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    boolean supportsModel(String modelType);

    /**
     * 功能描述: <br>
     * 〈构建异常信息〉
     *
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/28 16:10
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    Object buildErrMsg(Rule rule, String val, String msgTemp);

    /**
     * 功能描述: <br>
     * 〈消息处理类型，直接返回响应/将消息封装进线城中〉
     *
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/28 16:31
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */

    String msgType();
}