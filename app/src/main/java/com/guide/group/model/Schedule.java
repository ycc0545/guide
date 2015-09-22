package com.guide.group.model;

import java.io.Serializable;

public class Schedule implements Serializable {
    private String url;
    private String iconUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
