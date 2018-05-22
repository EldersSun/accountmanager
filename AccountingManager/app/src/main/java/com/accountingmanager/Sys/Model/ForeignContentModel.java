package com.accountingmanager.Sys.Model;

import java.io.Serializable;

/**
 * 外汇mod
 * Created by Home-Pc on 2017/5/16.
 */

public class ForeignContentModel implements Serializable {

    private static final long serialVersionUID = 5922230103998135902L;

    private int imgResources;
    private String name;
    private String company;

    public int getImgResources() {
        return imgResources;
    }

    public void setImgResources(int imgResources) {
        this.imgResources = imgResources;
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
}
