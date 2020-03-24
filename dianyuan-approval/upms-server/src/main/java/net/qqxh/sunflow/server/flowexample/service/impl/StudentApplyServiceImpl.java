package net.qqxh.sunflow.server.flowexample.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qqxh.sunflow.server.flowexample.bean.StudentApply;
import net.qqxh.sunflow.server.flowexample.mapper.StudentApplyMapper;
import net.qqxh.sunflow.server.flowexample.service.StudentApplyService;
import org.springframework.stereotype.Service;

/**
 * 报名信息实现类
 *
 * @author
 * @create 2019-06-14 14:49
 **/
@Service
public class StudentApplyServiceImpl extends ServiceImpl<StudentApplyMapper,StudentApply> implements StudentApplyService {
}
