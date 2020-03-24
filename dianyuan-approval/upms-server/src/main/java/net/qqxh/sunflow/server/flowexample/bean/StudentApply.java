package net.qqxh.sunflow.server.flowexample.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生报名实体类
 *
 * @author
 * @create 2019-06-14 14:29
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("flow_studentapply")
public class StudentApply implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 记录主键
     */
    @TableId(type = IdType.INPUT)
    private String recordid;
    /**
     * 学校名称
     */
    private String schoolname;
    /**
     * 年级
     */
    private String region;
    /**
     * 申请人
     */
    private String applyer;
    /**
     * 报名日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateday;

    /**
     * 时间
     */
    private String datetime;
    /**
     * 报名性质
     */
    private String[] bmtype;
    /**
     * 报名类型
     */
    private String resource;
    /**
     * 报名备注
     */
    private String descript;

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public Date getDateday() {
        return dateday;
    }

    public void setDateday(Date dateday) {
        this.dateday = dateday;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String[] getBmtype() {
        return bmtype;
    }

    public void setBmtype(String[] bmtype) {
        this.bmtype = bmtype;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
