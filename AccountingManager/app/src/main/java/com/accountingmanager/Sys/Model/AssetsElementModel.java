package com.accountingmanager.Sys.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Home-Pc on 2017/4/25.
 */
@Entity
public class AssetsElementModel implements Serializable{

    private static final long serialVersionUID = -8397224123918565422L;
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "MENU_ICON")
    private Integer menuIcon;
    @Property(nameInDb = "MENU_NAME")
    private String menuName;
    @Property(nameInDb = "TYPE")
    private String type;
    @Property(nameInDb = "MENU_TYPE")
    private String menuType;
    @Property(nameInDb = "AMOUNT")
    private String amount;
    @Property(nameInDb = "MARK")
    private String mark;
    @Property(nameInDb = "GROUP_NAME")
    private String groupName;
    @Property(nameInDb = "GROUP_ICON")
    private Integer groupIcon;

    @Generated(hash = 993298386)
    public AssetsElementModel() {
    }

    @Generated(hash = 618905260)
    public AssetsElementModel(Long id, Integer menuIcon, String menuName,
            String type, String menuType, String amount, String mark,
            String groupName, Integer groupIcon) {
        this.id = id;
        this.menuIcon = menuIcon;
        this.menuName = menuName;
        this.type = type;
        this.menuType = menuType;
        this.amount = amount;
        this.mark = mark;
        this.groupName = groupName;
        this.groupIcon = groupIcon;
    }

    public Integer getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(Integer groupIcon) {
        this.groupIcon = groupIcon;
    }

    public Integer getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(Integer menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
