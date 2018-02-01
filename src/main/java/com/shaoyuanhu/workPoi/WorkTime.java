package com.shaoyuanhu.workPoi;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ShaoYuanHu
 * @Description:
 * @Date: Create in 2018-02-01 09:50
 */
public class WorkTime implements Serializable {

    /** 姓名 */
    private String name;
    /** 打卡日期 */
    private Date today;
    /** 签到时间 */
    private Date beginTime;
    /** 签退时间 */
    private Date endTime;
    /** 工作时长 */
    private String workTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    @Override
    public String toString() {
        return "WorkTime{" +
                "name='" + name + '\'' +
                ", today=" + today +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", workTime='" + workTime + '\'' +
                '}';
    }
}
