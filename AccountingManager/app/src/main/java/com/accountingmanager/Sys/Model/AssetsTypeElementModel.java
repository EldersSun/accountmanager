package com.accountingmanager.Sys.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页资产分类视图model
 * Created by Home-Pc on 2017/4/27.
 */

public class AssetsTypeElementModel implements Serializable{
    private static final long serialVersionUID = 4894123037775403278L;
    private String groupName;
    private int menuIcon;
    private String type;

    private List<AssetsElementModel> assetsElementModelList = new ArrayList<>();

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<AssetsElementModel> getAssetsElementModelList() {
        return assetsElementModelList;
    }

    public void setAssetsElementModelList(List<AssetsElementModel> assetsElementModelList) {
        this.assetsElementModelList = assetsElementModelList;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
