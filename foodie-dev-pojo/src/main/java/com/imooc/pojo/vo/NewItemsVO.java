package com.imooc.pojo.vo;

import java.util.List;

/**
 * 二级分类Vo
 */
public class NewItemsVO {
    private  Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private  String catImage;
    private  String bgColor;

    public Integer getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(Integer rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getRootCatName() {
        return rootCatName;
    }

    public void setRootCatName(String rootCatName) {
        this.rootCatName = rootCatName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SimpleItemList> getSimpleItemList() {
        return simpleItemList;
    }

    public void setSimpleItemList(List<SimpleItemList> simpleItemList) {
        this.simpleItemList = simpleItemList;
    }

    /**
     * 三级分类vo
     */
    private List<SimpleItemList> simpleItemList;

}
