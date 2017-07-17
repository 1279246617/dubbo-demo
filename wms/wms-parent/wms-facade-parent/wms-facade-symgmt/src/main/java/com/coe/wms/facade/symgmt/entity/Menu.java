package com.coe.wms.facade.symgmt.entity;

import java.io.Serializable;

public class Menu implements Serializable {
    private Long id;

    private String menuCode;

    private String menuName;

    private Long parentId;

    private String menuEvent;

    private Integer menuSortNo;

    private String dictMenuStatus;

    private Long menuRightCode;

    private Integer menuRightPos;

    private String dictIsCommon;

    private String dictMenuType;

    private String createOperator;

    private Long createTime;

    private String updateOperator;

    private Long updateTime;

    private static final long serialVersionUID = 1L;

    public Menu() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuEvent() {
        return menuEvent;
    }

    public void setMenuEvent(String menuEvent) {
        this.menuEvent = menuEvent == null ? null : menuEvent.trim();
    }

    public Integer getMenuSortNo() {
        return menuSortNo;
    }

    public void setMenuSortNo(Integer menuSortNo) {
        this.menuSortNo = menuSortNo;
    }

    public String getDictMenuStatus() {
        return dictMenuStatus;
    }

    public void setDictMenuStatus(String dictMenuStatus) {
        this.dictMenuStatus = dictMenuStatus == null ? null : dictMenuStatus.trim();
    }

    public Long getMenuRightCode() {
        return menuRightCode;
    }

    public void setMenuRightCode(Long menuRightCode) {
        this.menuRightCode = menuRightCode;
    }

    public Integer getMenuRightPos() {
        return menuRightPos;
    }

    public void setMenuRightPos(Integer menuRightPos) {
        this.menuRightPos = menuRightPos;
    }

    public String getDictIsCommon() {
        return dictIsCommon;
    }

    public void setDictIsCommon(String dictIsCommon) {
        this.dictIsCommon = dictIsCommon == null ? null : dictIsCommon.trim();
    }

    public String getDictMenuType() {
        return dictMenuType;
    }

    public void setDictMenuType(String dictMenuType) {
        this.dictMenuType = dictMenuType == null ? null : dictMenuType.trim();
    }

    public String getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(String createOperator) {
        this.createOperator = createOperator == null ? null : createOperator.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator == null ? null : updateOperator.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}