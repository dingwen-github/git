package net.qqxh.sunflow.controller;
import net.qqxh.sunflow.camunda.process.constant.ProjectProcessConstant;
import net.qqxh.sunflow.util.StringPareseMap;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
    private final TaskService taskService;
    private final RuntimeService runtimeService;

    private static class ProjectParticipateRequestRecord {
        Long studentId;

        Long projectParticipateId;

        String taskId;

        public Long getProjectParticipateId() {
            return projectParticipateId;
        }

        public Long getStudentId() {
            return studentId;
        }

        public void setProjectParticipateId(Long projectParticipateId) {
            this.projectParticipateId = projectParticipateId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }

    private static class UploadExtraInfoRecord {
        private String taskId;

        private String theUploadUrlOfExtraInfo;

        public String getTaskId() {
            return taskId;
        }

        public String getTheUploadUrlOfExtraInfo() {
            return theUploadUrlOfExtraInfo;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public void setTheUploadUrlOfExtraInfo(String theUploadUrlOfExtraInfo) {
            this.theUploadUrlOfExtraInfo = theUploadUrlOfExtraInfo;
        }
    }

    public ProjectController(TaskService taskService, RuntimeService runtimeService) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
    }

    /*
        * 功能描述: <br>
        * 项目报名
        *
         * @param projectId
     * @param userId
        * @return boolean
        * @throws
        * @Author hpz
        * @date 2019-6-12 17:28
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
    //project,1
    @PostMapping(value = "/{projectId}")
    public boolean ParticipatingProject(@PathVariable String projectId, HttpEntity<String> requestEntity) {
        String bodyJson = requestEntity.getBody();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables = StringPareseMap.Split(bodyJson) ;
        //ignore argument verify

        //save the record to db

        Long savedRecordId = 3L;
        //start a new instance of the process
        /*variables.put(ProjectProcessConstant.VAR_NAME_SCHOOL, "上海交通大学");
        variables.put(ProjectProcessConstant.VAR_NAME_STUDENT, String.valueOf(userId));
        variables.put(ProjectProcessConstant.FORM_RECORD_ID, savedRecordId);*/

        ProcessInstance instance = runtimeService.
                startProcessInstanceByKey(projectId, variables);
        if (instance == null) {
            return false;
        }else {
            return true;
        }
    }

    /*
        * 功能描述: <br>
        * 获取需要审批的项目申请列表
        *
         * @param schoolName
     * @param reviewLevel
        * @return java.util.List<net.qqxh.sunFlow.controller.ProjectController.ProjectParticipateRequestRecord>
        * @throws
        * @Author hpz
        * @date 2019-6-12 17:29
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
    @GetMapping(value = "/project/approve/list")
    public @ResponseBody List<ProjectParticipateRequestRecord> getAllProjectParticipateRequest(String schoolName, Integer reviewLevel) {

        LOGGER.info("The school name is {}", schoolName);
        //get the taskList
        List<Task> tasks;
        if (reviewLevel.equals(1)) {
             tasks = taskService.createTaskQuery().
                taskName(ProjectProcessConstant.TASK_NAME_FIRST_LEVEL_REVIEW).
                            taskCandidateGroup(schoolName).
                            list();
        }else {
            tasks = taskService.createTaskQuery().
                    taskName(ProjectProcessConstant.TASK_NAME_SECOND_LEVEL_REVIEW).
                    taskCandidateGroup(schoolName).
                    list();
        }

        List<ProjectParticipateRequestRecord> records = new ArrayList<ProjectParticipateRequestRecord>(tasks.size());
        tasks.forEach( task -> {
            ProjectParticipateRequestRecord record = new ProjectParticipateRequestRecord();
            String taskId = task.getId();
            Map<String, Object> variables = taskService.getVariables(taskId);

            Long studentId = Long.valueOf ( (String)variables.get(ProjectProcessConstant.VAR_NAME_STUDENT) );
            Long recordId = (Long) variables.get(ProjectProcessConstant.FORM_RECORD_ID);
            record.setStudentId(studentId);
            record.setProjectParticipateId(recordId);
            record.setTaskId(taskId);

            records.add(record);
        });

        return records;
    }

    /*
        * 功能描述: <br>
        * 审批项目申请
        *
         * @param taskId
     * @param needExtraInfo
     * @param passed
     * @param schoolName
        * @return boolean
        * @throws
        * @Author hpz
        * @date 2019-6-12 17:29
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
    @PutMapping(value = "/project/participateRequests/{taskId}")
    public boolean approveProjectParticipateRequest(@PathVariable String taskId, boolean needExtraInfo, boolean passed, String schoolName) {
        Task task = taskService.createTaskQuery().
                taskCandidateGroup(schoolName).taskId(taskId).singleResult();
        if (task == null) {
            LOGGER.error("The task not found, task id is {}", taskId);
            return false;
        }else {
            //business logic here

            //Into next step
            LOGGER.info("The taskId is {}", taskId);
            Map<String, Object> variables = new HashMap<>();
            variables.put(ProjectProcessConstant.FORM_EXTRA_INFO_1,  needExtraInfo);
            variables.put(ProjectProcessConstant.FORM_APPROVED_1, passed);
            taskService.complete(task.getId(), variables);
            return true;
        }
    }

    /*
        * 功能描述: <br>
        * 获取学生需要上传额外材料的记录
        *
         * @param userId
        * @return java.util.List<net.qqxh.sunFlow.controller.ProjectController.UploadExtraInfoRecord>
        * @throws
        * @Author hpz
        * @date 2019-6-12 17:29
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
    @GetMapping(value = "/users/{userId}/extraInfo/list")
    public List<UploadExtraInfoRecord> getUploadExtraTask(Long userId) {
        List<Task> uploadExtraInfoTask =
                taskService.createTaskQuery().
                        taskAssignee(String.valueOf(userId)).
                        taskName(ProjectProcessConstant.TASK_NAME_UPLOAD_EXTRA_INFO).
                        list();

        List<UploadExtraInfoRecord> records = new ArrayList<>(uploadExtraInfoTask.size());
        uploadExtraInfoTask.forEach( task -> {
            UploadExtraInfoRecord record = new UploadExtraInfoRecord();
            record.setTaskId(task.getId());

            //the upload url of extra info is up to the variable
            record.setTheUploadUrlOfExtraInfo("www.google.com");

            records.add(record);
        });

        return records;
    }

    /*
        * 功能描述: <br>
        * 上传指定项目所需的额外资料
        *
         * @param projectId
     * @param userId
     * @param extraInfo
     * @param taskId
        * @return boolean
        * @throws
        * @Author hpz
        * @date 2019-6-12 17:29
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
    @PostMapping(value = "/{projectId}/users/{userId}/extraInfo")
    public boolean  uploadExtraInfo(@PathVariable Long projectId, @PathVariable Long userId,  String extraInfo, String taskId) {
        //must verify the task of the taskId pointing is belong the current user.
        Task task = taskService.createTaskQuery().
                taskAssignee(String.valueOf(userId)).
                taskName(ProjectProcessConstant.TASK_NAME_UPLOAD_EXTRA_INFO).
                taskId(taskId).
                singleResult();
        if (task == null) {
            LOGGER.error("The task not found.");
            LOGGER.error("the assignee is {}, taskName is {}, taskId is {}.", userId, ProjectProcessConstant.TASK_NAME_UPLOAD_EXTRA_INFO, taskId);
            return false;
        }else {
            //upload extra info to db.

            //business logic here

            //into next step
            taskService.complete(task.getId());
            return true;
        }
    }
}
