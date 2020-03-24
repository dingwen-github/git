package net.qqxh.sunflow.server.upms.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈用户部门关联表〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: UserDept.java
 * @date: 2019/5/29 20:16
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("supms_user_dept")
public class UserDept implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private String userId;
    @TableId
    private Integer deptId;
    /**
     * 岗位id
     */
    @TableId
    private String postId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
