package net.qqxh.sunflow.server.dictionary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.server.dictionary.bean.DictionaryDetail;

import java.util.List;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 数据字典明细 服务类
 *
 * @author cjy
 * @fileName DictionaryDetailService.java
 * @date 2019/5/25 17:59
 */
public interface DictionaryDetailService extends IService<DictionaryDetail> {

    /**
     * 根据数据字典目录编码获取字典明细列表
     *
     * @param catalogCode 字典编码
     * @return 字典明细列表
     * @author cjy
     * @date 2019/6/18 21:52
     */
    List<DictionaryDetail> getDetailList(String catalogCode);

    /**
     * 删除明细 根据数据字典目录编码、数据编码
     *
     * @param catalogCode 字典编码
     * @param dataCode    数据编码
     * @return 是否成功
     */
    boolean remove(String catalogCode, String dataCode);

    /**
     * 删除明细 根据数据字典目录编码
     *
     * @param catalogCode 字典编码
     * @return 是否成功
     */
    boolean remove(String catalogCode);
}
