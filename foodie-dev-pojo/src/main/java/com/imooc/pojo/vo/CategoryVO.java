package com.imooc.pojo.vo;

import java.util.List;

/**
 * 二级分类Vo
 */
public class CategoryVO {

    private  Integer id;
    private String name;
    private String type;
    private  Integer fatherID;
    /**
     * 三级分类vo
     */
    private List<SubCategoryVO> subCatList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFatherID() {
        return fatherID;
    }

    public void setFatherID(Integer fatherID) {
        this.fatherID = fatherID;
    }

    public List<SubCategoryVO> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<SubCategoryVO> subCatList) {
        this.subCatList = subCatList;
    }
}
