package com.imooc.vo;

/**
 * @ClassName SubCategoryVO
 * @Description 商品分类三级分类
 * @Author braveZeng
 * @Date 14:52 2022/12/2
 * Version 1.0
 **/
public class SubCategoryVO {
    private Integer subId;
    private String subName;
    private String subType;

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(Integer subFatherId) {
        this.subFatherId = subFatherId;
    }

    private Integer subFatherId;



}
