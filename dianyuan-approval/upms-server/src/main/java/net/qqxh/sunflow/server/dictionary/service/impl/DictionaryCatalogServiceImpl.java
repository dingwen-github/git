package net.qqxh.sunflow.server.dictionary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.dictionary.bean.DictionaryCatalog;
import net.qqxh.sunflow.server.dictionary.mapper.DictionaryCatalogMapper;
import net.qqxh.sunflow.server.dictionary.service.DictionaryCatalogService;
import net.qqxh.sunflow.server.dictionary.service.DictionaryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 数据字典目录 服务类实现
 *
 * @fileName DictionaryCatalogServiceImpl.java
 * @date     2019/5/25 15:57
 * @author   cjy
 */
@Service
public class DictionaryCatalogServiceImpl extends ServiceImpl<DictionaryCatalogMapper, DictionaryCatalog> implements DictionaryCatalogService {
    @Autowired
    DictionaryDetailService dictionaryDetailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) {
        super.removeById(id);
        return dictionaryDetailService.remove(id);
    }
}
