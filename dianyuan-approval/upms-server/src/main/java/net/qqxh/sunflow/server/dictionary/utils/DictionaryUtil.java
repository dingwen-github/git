package net.qqxh.sunflow.server.dictionary.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.qqxh.sunflow.server.dictionary.bean.DictionaryCatalog;
import net.qqxh.sunflow.server.dictionary.bean.DictionaryDetail;
import net.qqxh.sunflow.server.dictionary.service.DictionaryCatalogService;
import net.qqxh.sunflow.server.dictionary.service.DictionaryDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 数据字典 工具类
 *
 * @author cjy
 * @fileName DictionaryUtil.java
 * @date 2019/5/25 16:02
 */
@Component
public class DictionaryUtil {
    @Autowired
    private DictionaryCatalogService dictionaryCatalogService;
    @Autowired
    private DictionaryDetailService dictionaryDetailService;

    public static DictionaryUtil dictionaryUtil;

    @PostConstruct
    public void init() {
        dictionaryUtil = this;
    }

    /**
     * 根据数据字典目录编码和数据编码获取数据值
     *
     * @param catalogCode 字典编码
     * @param dataCode 数据编码
     * @return 数据值
     * @author cjy
     * @date 2019/5/25 16:52
     */
    public static String getValue(String catalogCode, String dataCode) {
        if(StringUtils.isBlank(catalogCode) || StringUtils.isBlank(dataCode)) {
            return null;
        }
        String value = null;
        DictionaryDetail dictionaryDetail = getDetail(catalogCode, dataCode);
        if(dictionaryDetail != null) {
            value = dictionaryDetail.getDataValue();
        }
        return value;
    }

    /**
     * 根据数据字典目录编码和数据值获取数据编码
     *
     * @param catalogCode 字典编码
     * @param dataValue 数据值
     * @return 数据编码
     * @author cjy
     * @date 2019/5/25 16:52
     */
    public static String getCode(String catalogCode, String dataValue) {
        if (StringUtils.isBlank(catalogCode) || StringUtils.isBlank(dataValue)) {
            return null;
        }
        String code = null;
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<DictionaryDetail>();
        queryWrapper.eq("catalog_code", catalogCode);
        queryWrapper.eq("data_value", dataValue);
        DictionaryDetail dictionaryDetail = dictionaryUtil.dictionaryDetailService.getOne(queryWrapper);
        if (dictionaryDetail != null) {
            code = dictionaryDetail.getDataCode();
        }
        return code;
    }

    /**
     * 根据数据字典目录编码和数据编码获取数据值
     *
     * @param catalogCode 字典编码
     * @param dataCode 数据编码
     * @return 字典明细
     * @author cjy
     * @date 2019/5/25 16:52
     */
    public static DictionaryDetail getDetail(String catalogCode, String dataCode) {
        if(StringUtils.isBlank(catalogCode) || StringUtils.isBlank(dataCode)) {
            return null;
        }
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<DictionaryDetail>();
        queryWrapper.eq("catalog_code", catalogCode);
        queryWrapper.eq("data_code", dataCode);
        DictionaryDetail dictionaryDetail = dictionaryUtil.dictionaryDetailService.getOne(queryWrapper);
        return dictionaryDetail;
    }

    /**
     * 根据数据字典目录编码获取字典明细列表
     *
     * @param catalogCode 字典编码
     * @return 字典明细列表
     * @author cjy
     * @date 2019/5/25 16:52
     */
    public static List<DictionaryDetail> getDetailList(String catalogCode) {
        if(StringUtils.isBlank(catalogCode)) {
            return null;
        }
        List<DictionaryDetail> detailList = dictionaryUtil.dictionaryDetailService.getDetailList(catalogCode);

        if(isTreeStruct(catalogCode)) {
            return formatTree(detailList);
        } else {
            return detailList;
        }
    }

    /**
     * 根据数据字典目录编码判断是否树形
     *
     * @param catalogCode 字典编码
     * @return 是否树形结构
     * @author cjy
     * @date 2019/6/18 21:52
     */
    public static boolean isTreeStruct(String catalogCode) {
        if(StringUtils.isBlank(catalogCode)) {
            return false;
        }
        QueryWrapper<DictionaryCatalog> queryWrapper = new QueryWrapper<DictionaryCatalog>();
        queryWrapper.eq("catalog_code", catalogCode);
        DictionaryCatalog dictionaryCatalog = dictionaryUtil.dictionaryCatalogService.getOne(queryWrapper);
        if(dictionaryCatalog != null && "T".equals(dictionaryCatalog.getCatalogStruct())) {
            return true;
        } else{
            return false;
        }
    }

    /**
     * 是否存在数据字典
     *
     * @param catalogCode 字典编码
     * @return 是否存在
     * @author cjy
     * @date 2019/5/25 23:28
     */
    public static boolean hasCatalog(String catalogCode) {
        if (StringUtils.isBlank(catalogCode)) {
            return false;
        }
        DictionaryCatalog dictionaryCatalog = dictionaryUtil.dictionaryCatalogService.getById(catalogCode);
        return dictionaryCatalog == null ? false : true;
    }

    /**
     * 是否存在字典明细项
     *
     * @param catalogCode 字典编码
     * @param dataCode    数据编码
     * @return 是否存在
     * @author cjy
     * @date 2019/5/25 23:28
     */
    public static boolean hasDetail(String catalogCode, String dataCode) {
        if (StringUtils.isBlank(catalogCode) || StringUtils.isBlank(dataCode)) {
            return false;
        }
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<DictionaryDetail>();
        queryWrapper.eq("catalog_code", catalogCode);
        queryWrapper.eq("data_code", dataCode);
        DictionaryDetail dictionaryDetail = dictionaryUtil.dictionaryDetailService.getOne(queryWrapper);
        return dictionaryDetail == null ? false : true;
    }

    /**
     * 字典明细键值对
     *
     * @param catalogCode 字典编码
     * @return 键值对map
     * @author cjy
     * @date 2019/5/25 23:28
     */
    public static Map<String, String> getKeyValueMap(String catalogCode) {
        if (StringUtils.isBlank(catalogCode)) {
            return null;
        }
        Map<String, String> kvMap = new HashMap<String, String>(4);
        List<DictionaryDetail> dictionaryDetailList = getDetailList(catalogCode);
        for (DictionaryDetail dictionaryDetail : dictionaryDetailList) {
            kvMap.put(dictionaryDetail.getDataCode(), dictionaryDetail.getDataValue());
        }
        return kvMap;
    }

    /**
     * 所有字典编码
     *
     * @return 字典编码列表
     * @author cjy
     * @date 2019/5/25 23:28
     */
    public static List<String> getAllCatalogCodes() {
        List<String> list = new ArrayList<>();
        List<DictionaryCatalog> dictionaryCatalogList = dictionaryUtil.dictionaryCatalogService.list();
        for (DictionaryCatalog dictionaryCatalog : dictionaryCatalogList) {
            list.add(dictionaryCatalog.getCatalogCode());
        }
        return list;
    }

    // 转为树形结构
    private static List<DictionaryDetail> formatTree(List<DictionaryDetail> dictionaryDetails) {
        Iterator<DictionaryDetail> detailIterator = dictionaryDetails.iterator();
        List<DictionaryDetail> detailList = new ArrayList<>();
        while (detailIterator.hasNext()) {
            DictionaryDetail detail = detailIterator.next();
            boolean hasParent = false;
            for (DictionaryDetail d : dictionaryDetails) {
                if (d.getDataCode().equals(detail.getDataParent())) {
                    if (d.getChildren() == null) {
                        d.setChildren(new ArrayList<>());
                    }
                    d.getChildren().add(detail);
                    hasParent = true;
                    break;
                }
            }
            if (!hasParent) {
                detailList.add(detail);
            }
        }
        return detailList;
    }
}
