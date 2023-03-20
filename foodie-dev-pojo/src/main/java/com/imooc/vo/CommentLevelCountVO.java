package com.imooc.vo;

/**
 * 用于展示商品评价数据的VO
 */
public class CommentLevelCountVO {

    /**
     * 商品总评价数量
     */
    private Integer totalCounts;
    /**
     * 商品好评数量
     */
    private Integer goodCounts;

    /**
     * 商品中评数量
     */
    private Integer normalCounts;
    /**
     * 商品差评数量
     */
    private Integer badCounts;


    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Integer goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Integer badCounts) {
        this.badCounts = badCounts;
    }
}