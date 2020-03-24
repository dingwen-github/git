package net.qqxh.sunflow.server.dictionary.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.qqxh.sunflow.logging.annotation.Log;
import net.qqxh.sunflow.server.common.BaseController;
import net.qqxh.sunflow.server.dictionary.bean.DictionaryCatalog;
import net.qqxh.sunflow.server.dictionary.bean.DictionaryDetail;
import net.qqxh.sunflow.server.dictionary.service.DictionaryCatalogService;
import net.qqxh.sunflow.server.dictionary.service.DictionaryDetailService;
import net.qqxh.sunflow.server.dictionary.utils.DictionaryUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 数据字典 前端控制器
 *
 * @author cjy
 * @fileName DictionaryController.java
 * @date 2019/5/25 10:22
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController<DictionaryCatalog> {
    @Autowired
    DictionaryCatalogService dictionaryCatalogService;
    @Autowired
    DictionaryDetailService dictionaryDetailService;

    /**
     * 数据字典目录 列表
     *
     * @param request {@link HttpServletRequest}
     * @param current 页码
     * @param size    每页条数
     * @return 数据字典目录列表Json
     * @author cjy
     * @date 2019/5/25 10:25
     */
    @Log("查询数据字典目录列表")
    @RequestMapping("/list")
    public Object list(HttpServletRequest request, int current, int size) {
        QueryWrapper<DictionaryCatalog> queryWrapper = new QueryWrapper<DictionaryCatalog>();
        String sCatalogCode = request.getParameter("catalogCode");
        String sCatalogName = request.getParameter("catalogName");
        String sCatalogStruct = request.getParameter("catalogStruct");
        if (StringUtils.isNotBlank(sCatalogCode)) {
            queryWrapper.like("catalog_code", sCatalogCode);
        }
        if (StringUtils.isNotBlank(sCatalogName)) {
            queryWrapper.like("catalog_name", sCatalogName);
        }
        if (StringUtils.isNotBlank(sCatalogStruct)) {
            queryWrapper.eq("catalog_struct", sCatalogStruct);
        }
        queryWrapper.orderByAsc("catalog_name");
        Page<DictionaryCatalog> page = new Page<DictionaryCatalog>(current, size);
        return responseSuccess(dictionaryCatalogService.page(page, queryWrapper));
    }

    /**
     * 数据字典目录 新增
     *
     * @param dictionaryCatalog 字典目录
     * @return 是否成功
     * @author cjy
     * @date 2019/5/25 16:05
     */
    @Log("新增数据字典目录")
    @PostMapping("/create")
    public Object create(DictionaryCatalog dictionaryCatalog) {
        if (dictionaryCatalogService.getById(dictionaryCatalog.getCatalogCode()) != null) {
            return responseFail("字典编码已存在");
        }
        dictionaryCatalog.setCreateDate(new Date());
        dictionaryCatalog.setUpdateDate(new Date());
        return responseSuccess(dictionaryCatalogService.save(dictionaryCatalog));
    }

    /**
     * 数据字典目录 更新
     *
     * @param dictionaryCatalog 字典目录
     * @return 是否成功
     * @author cjy
     * @date 2019/5/25 16:05
     */
    @Log("更新数据字典目录")
    @PostMapping("/update")
    public Object update(DictionaryCatalog dictionaryCatalog) {
        dictionaryCatalog.setUpdateDate(new Date());
        return responseSuccess(dictionaryCatalogService.saveOrUpdate(dictionaryCatalog));
    }

    /**
     * 数据字典目录 删除
     *
     * @param catalogCode 字典编码
     * @return 是否成功
     * @author cjy
     * @date 2019/5/25 16:05
     */
    @Log("删除数据字典目录")
    @PostMapping("/delete/{catalogCode}")
    public Object delete(@PathVariable String catalogCode) {
        return responseSuccess(dictionaryCatalogService.removeById(catalogCode));
    }

    /**
     * 数据字典明细 列表
     *
     * @param catalogCode 字典目录编码
     * @return 数据字典明细列表json
     * @author cjy
     * @date 2019/5/25 16:34
     */
    @Log("查询数据字典明细列表")
    @GetMapping("/listDetail/{catalogCode}")
    public Object listDetail(@PathVariable String catalogCode) {
        return responseSuccess(DictionaryUtil.getDetailList(catalogCode));
    }

    /**
     * 数据字典明细 新增
     *
     * @param dictionaryDetail 字典明细
     * @return 是否成功
     * @author cjy
     * @date 2019/5/25 16:34
     */
    @Log("新增数据字典明细")
    @PostMapping("/createDetail")
    public Object createDetail(DictionaryDetail dictionaryDetail) {
        if (DictionaryUtil.hasDetail(dictionaryDetail.getCatalogCode(), dictionaryDetail.getDataCode())) {
            return responseFail("数据编码已存在");
        }
        dictionaryDetail.setCreateDate(new Date());
        dictionaryDetail.setUpdateDate(new Date());
        return responseSuccess(dictionaryDetailService.save(dictionaryDetail));
    }

    /**
     * 数据字典明细 更新
     *
     * @param dictionaryDetail 字典明细
     * @return 是否成功
     * @author cjy
     * @date 2019/5/25 16:34
     */
    @Log("更新数据字典明细")
    @PostMapping("/updateDetail")
    public Object updateDetail(DictionaryDetail dictionaryDetail) {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<DictionaryDetail>();
        queryWrapper.eq("catalog_code", dictionaryDetail.getCatalogCode());
        queryWrapper.eq("data_code", dictionaryDetail.getDataCode());
        dictionaryDetail.setUpdateDate(new Date());
        return responseSuccess(dictionaryDetailService.update(dictionaryDetail, queryWrapper));
    }

    /**
     * 数据字典明细 删除
     *
     * @param catalogCode 字典编码
     * @param dataCode    数据编码
     * @return 是否成功
     * @author cjy
     * @date 2019/5/25 16:34
     */
    @Log("删除数据字典明细")
    @PostMapping("/deleteDetail/{catalogCode}/{dataCode}")
    public Object deleteDetail(@PathVariable String catalogCode, @PathVariable String dataCode) {
        return responseSuccess(dictionaryDetailService.remove(catalogCode, dataCode));
    }

    /**
     * 获取指定字典编码集的字典明细集合
     *
     * @param catalogCodes 字典编码（','分隔）
     * @return 字典明细集合
     * @author cjy
     * @date 2019/5/25 16:46
     */
    @GetMapping("/getDetailSetByCatalogcodes")
    public Object getDetailSetByCatalogcodes(@RequestParam String catalogCodes) {
        String[] catalogCodeList = catalogCodes.split(",");
        JSONObject obj = new JSONObject();
        for (String catalogCode : catalogCodeList) {
            List<DictionaryDetail> dictionaryDetailList = DictionaryUtil.getDetailList(catalogCode);
            obj.put(catalogCode, dictionaryDetailList);
        }
        return responseSuccess(obj);
    }

    /**
     * 是否存在数据字典
     *
     * @param catalogCode 字典编码
     * @return 是否存在
     * @author cjy
     * @date 2019/5/25 23:28
     */
    @GetMapping("/hasCatalogcode/{catalogCode}")
    public Object hasCatalogcode(@PathVariable String catalogCode) {
        return responseSuccess(DictionaryUtil.hasCatalog(catalogCode));
    }

}
