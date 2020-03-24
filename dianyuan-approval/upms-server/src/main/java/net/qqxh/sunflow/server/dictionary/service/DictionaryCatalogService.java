package net.qqxh.sunflow.server.dictionary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qqxh.sunflow.server.dictionary.bean.DictionaryCatalog;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 数据字典目录 服务类
 *
 * @fileName DictionaryCatalogService.java
 * @date     2019/5/25 15:37
 * @author   cjy
 */
public interface DictionaryCatalogService extends IService<DictionaryCatalog> {
    /**
     * removeById
     * @param id
     * @return
     */
    boolean removeById(String id);
}
