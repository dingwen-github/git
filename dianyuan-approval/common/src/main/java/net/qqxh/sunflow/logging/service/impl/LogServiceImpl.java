package net.qqxh.sunflow.logging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.logging.bean.SfLog;
import net.qqxh.sunflow.logging.mapper.SfLogMapper;
import net.qqxh.sunflow.logging.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jwy
 * @since 2019-05-23
 */
@Service
public class LogServiceImpl extends ServiceImpl<SfLogMapper, SfLog> implements LogService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SfLog sfLog) {
        //如果标记了储存入库得，保存入库
        return super.save(sfLog);
    }
}
