package com.guide.group.model;

import com.guide.base.SimpleResult;

import java.util.List;

/**
 * Created by mac on 7/9/15.
 */
public class GetGroupByCodeResult extends SimpleResult {
    private Group group;
    private String success;
    private List<String> scheduleTable;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<String> getScheduleTable() {
        return scheduleTable;
    }

    public void setScheduleTable(List<String> scheduleTable) {
        this.scheduleTable = scheduleTable;
    }
}
