package net.qqxh.sunflow.server.upms.mapper;

import net.qqxh.sunflow.mapper.SuperMapper;
import net.qqxh.sunflow.server.upms.bean.Router;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface RouterMapper extends SuperMapper<Router> {
    List<Router>list();
    List<Router>listByRole(Set<String> set);
}
