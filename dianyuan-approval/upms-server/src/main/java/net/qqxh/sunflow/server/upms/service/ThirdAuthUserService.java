package net.qqxh.sunflow.server.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.server.upms.bean.ThirdAuthUser;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈第三方权限登陆信息表service〉<br>
 * 〈功能详细描述〉
 *
 * @author jwy
 * @fileName: ThirdAuthUserService.java
 * @date: 2019/5/29 20:49
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ThirdAuthUserService extends IService<ThirdAuthUser> {
    ShiroUser getOrRegisterUser(ThirdAuthUser user);
}
