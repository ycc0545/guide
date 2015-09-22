package com.guide.user.model;

import com.guide.group.model.Guide;
import com.guide.group.model.RateInfoVo;

import java.io.Serializable;

/**
 * Created by Xingkai on 14/9/15.
 */
public class Follow implements Serializable {
    private Guide guide;
    private RateInfoVo rateInfoVo;
    private int serviceTouristCounts;
    private int serviceGroupCounts;

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
}
