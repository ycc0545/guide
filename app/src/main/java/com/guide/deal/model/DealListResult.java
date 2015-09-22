package com.guide.deal.model;

import com.guide.base.SimpleResult;

import java.util.List;

/**
 * Created by mac on 10/9/15.
 */
public class DealListResult extends SimpleResult {
    private List<Deal> deals;

    public List<Deal> getDealList() {
        return deals;
    }

    public void setDealList(List<Deal> deals) {
        this.deals = deals;
    }
}
