package com.guide.action;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 9/9/15.
 */
public class Items implements Serializable {
    private int index;
    private List<Item> itemsList;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }
}
