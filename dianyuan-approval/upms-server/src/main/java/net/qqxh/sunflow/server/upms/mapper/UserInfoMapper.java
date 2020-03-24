package net.qqxh.sunflow.server.upms.mapper;

import net.qqxh.sunflow.mapper.SuperMapper;
import net.qqxh.sunflow.server.upms.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends SuperMapper<UserInfo> {
    UserInfo findByLoginName(String loginName);
    int checkLoginNameUnique(UserInfo userInfo);
}
