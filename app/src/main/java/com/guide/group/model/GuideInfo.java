package com.guide.group.model;

import com.guide.deal.model.Deal;

import java.util.List;

/**
 * Result model for api: .../tourists/guides/{guideId}
 * <p/>
 * Created by Jason on 15/9/8.
 */
public class GuideInfo {

    private Guide guide;
    private RateInfoVo rateInfoVo;
    private int serviceTouristCounts;
    private int serviceGroupCounts;
    private List<Group> groups;
    private List<Deal> deals;

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public RateInfoVo getRateInfoVo() {
        return rateInfoVo;
    }

    public void setRateInfoVo(RateInfoVo rateInfoVo) {
        this.rateInfoVo = rateInfoVo;
    }

    public int getServiceTouristCounts() {
        return serviceTouristCounts;
    }

    public void setServiceTouristCounts(int serviceTouristCounts) {
        this.serviceTouristCounts = serviceTouristCounts;
    }

    public int getServiceGroupCounts() {
        return serviceGroupCounts;
    }

    public void setServiceGroupCounts(int serviceGroupCounts) {
        this.serviceGroupCounts = serviceGroupCounts;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }
}
