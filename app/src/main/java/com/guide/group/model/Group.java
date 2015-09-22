package com.guide.group.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 6/9/15.
 */
public class Group implements Serializable {
    private int groupId;
    private String name;
    private String company;
    private String startDate;
    private String endDate;
    private String createTime;
    private int creator;
    private int status;
    private String groupCode;
    private String scheduleTable;
    private List<Guide> guides;
    private String daysNights;

    public String getDaysNights() {
        return daysNights;
    }

    public void setDaysNights(String daysNights) {
        this.daysNights = daysNights;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getScheduleTable() {
        return scheduleTable;
    }

    public void setScheduleTable(String scheduleTable) {
        this.scheduleTable = scheduleTable;
    }

    public List<Guide> getGuides() {
        return guides;
    }

    public void setGuides(List<Guide> guides) {
        this.guides = guides;
    }
}
