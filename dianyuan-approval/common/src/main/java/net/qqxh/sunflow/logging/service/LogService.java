package net.qqxh.sunflow.logging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.logging.bean.SfLog;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jwy
 * @since 2019-05-23
 */
public interface LogService extends IService<SfLog> {
    /**
     * 新增日志
     *
     * @param sfLog
     * @Author jwy
     * @date 23/05/2019 15:41
     */
    @Async
    @Override
    boolean save(SfLog sfLog);
}
