package com.guide.group.model;

/**
 * Created by Jason on 15/9/8.
 */
public class RateInfoVo {

    private float score1;
    private float score2;
    private float score3;
    private float score4;
    private float score5;
    // average rating score
    private float score;
    // total amount of rating tourists
    private int ratingCount;

    public float getScore1() {
        return score1;
    }

    public void setScore1(float score1) {
        this.score1 = score1;
    }

    public float getScore2() {
        return score2;
    }

    public void setScore2(float score2) {
        this.score2 = score2;
    }

    public float getScore3() {
        return score3;
    }

    public void setScore3(float score3) {
        this.score3 = score3;
    }

    public float getScore4() {
        return score4;
    }

    public void setScore4(float score4) {
        this.score4 = score4;
    }

    public float getScore5() {
        return score5;
    }

    public void setScore5(float score5) {
        this.score5 = score5;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
