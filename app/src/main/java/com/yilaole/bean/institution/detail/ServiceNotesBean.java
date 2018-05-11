package com.yilaole.bean.institution.detail;

import java.io.Serializable;
import java.util.List;

/**
 * 服务须知
 */

public class ServiceNotesBean implements Serializable {
    private int id;
    private String livenotice;
    private List<String> service;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLivenotice() {
        return livenotice;
    }

    public void setLivenotice(String livenotice) {
        this.livenotice = livenotice;
    }

    public List<String> getService() {
        return service;
    }

    public void setService(List<String> service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ServiceNotesBean{" +
                "id=" + id +
                ", livenotice='" + livenotice + '\'' +
                ", service=" + service +
                '}';
    }
}
