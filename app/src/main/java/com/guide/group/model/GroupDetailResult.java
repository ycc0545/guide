package com.guide.group.model;

import com.guide.action.Items;
import com.guide.base.SimpleResult;

import java.util.List;

/**
 * Created by mac on 9/9/15.
 */
public class GroupDetailResult extends SimpleResult {
    private Group group;
    private Items items;
    private List<String> scheduleTable;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<String> getScheduleTable() {
        return scheduleTable;
    }

    public void setScheduleTable(List<String> scheduleTable) {
        this.scheduleTable = scheduleTable;
    }
}
