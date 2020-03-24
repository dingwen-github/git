package net.qqxh.sunflow.server.flowexample.controller;

import net.qqxh.sunflow.flowclient.FlowClient;
import net.qqxh.sunflow.logging.annotation.Log;
import net.qqxh.sunflow.server.common.BaseController;
import net.qqxh.sunflow.server.flowexample.bean.StudentApply;
import net.qqxh.sunflow.server.flowexample.service.StudentApplyService;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 学生报名业务控制层
 *
 * @author
 * @create 2019-06-14 14:20
 **/
@RestController
@RequestMapping("/studentApply")
public class StudentApplyController extends BaseController<StudentApply> {
    private final Logger LOGGER = LoggerFactory.getLogger(StudentApplyController.class);
    @Autowired
    StudentApplyService studentApplyService;
    @Log("学生报名表单信息保存，并且发起流程")
    @PostMapping("/saveinfo")
    public Object saveinfo(StudentApply studentApply){
        studentApply.setRecordid(UUID.randomUUID().toString());
        studentApply.setDateday(new Date());
        ShiroUser user=getLoginUser();
        studentApply.setApplyer(user.getLoginName());
        String url="http://localhost:8080/projects/project";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("school",studentApply.getSchoolname());
        params.put("student",studentApply.getApplyer());
        params.put("recordId",studentApply.getRecordid());
        try {
         String response= FlowClient.sendPostDataByMap(url,params,"utf-8");
         if("true".equals(response)){
             LOGGER.info("流程实例创建成功");
             studentApplyService.saveOrUpdate(studentApply);
             LOGGER.info("学生报名保存成功");
         }else {
             LOGGER.info("流程创建失败"+response);
         }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseSuccess(null);
    }
}
