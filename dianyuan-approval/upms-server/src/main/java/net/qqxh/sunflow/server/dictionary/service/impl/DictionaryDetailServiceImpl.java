package net.qqxh.sunflow.server.dictionary.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.dictionary.bean.DictionaryDetail;
import net.qqxh.sunflow.server.dictionary.mapper.DictionaryDetailMapper;
import net.qqxh.sunflow.server.dictionary.service.DictionaryDetailService;
import net.qqxh.sunflow.server.jedis.JedisPrefix;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 数据字典明细 服务类实现
 *
 * @fileName DictionaryDetailServiceImpl.java
 * @date     2019/5/25 18:03
 * @author   cjy
 */
@Service
public class DictionaryDetailServiceImpl extends ServiceImpl<DictionaryDetailMapper, DictionaryDetail> implements DictionaryDetailService {

    /**
     * 根据数据字典目录编码获取字典明细列表
     *
     * @param catalogCode 字典编码
     * @return 字典明细列表
     * @author cjy
     * @date 2019/6/18 21:52
     */
    @Override
    @Cacheable(value = JedisPrefix.DICTIONARY, key = "#catalogCode")
    public List<DictionaryDetail> getDetailList(String catalogCode) {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<DictionaryDetail>();
        queryWrapper.eq("catalog_code", catalogCode);
        queryWrapper.orderByAsc("data_order");
        return list(queryWrapper);
    }

    @Override
    @CacheEvict(value = JedisPrefix.DICTIONARY, key = "#p0")
    public boolean remove(String catalogCode, String dataCode) {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<DictionaryDetail>();
        queryWrapper.eq("catalog_code", catalogCode);
        queryWrapper.eq("data_code", dataCode);
        return super.remove(queryWrapper);
    }

    @Override
    @CacheEvict(value = JedisPrefix.DICTIONARY, key = "#p0")
    public boolean remove(String catalogCode) {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<DictionaryDetail>();
        queryWrapper.eq("catalog_code", catalogCode);
        return super.remove(queryWrapper);
    }

    @Override
    @CacheEvict(value = JedisPrefix.DICTIONARY, key = "#dictionaryDetail.catalogCode")
    public boolean save(DictionaryDetail dictionaryDetail) {
        return super.save(dictionaryDetail);
    }

    @Override
    @CacheEvict(value = JedisPrefix.DICTIONARY, key = "#dictionaryDetail.catalogCode")
    public boolean update(DictionaryDetail dictionaryDetail, Wrapper updateWrapper) {
        return super.update(dictionaryDetail, updateWrapper);
    }
}
