package com.search.entity;

import javax.validation.constraints.NotNull;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 10:44
 */
public class UpdateIndexReq {
    @NotNull
    private String id;
    @NotNull
    private String title;
    private String oldId;
    private String taobaoId;
    private String plat;
    private String shopName;
    private String keyWord;
    private String createTime;
    private String isRecommend;
    private String attention;
    private String discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getTaobaoId() {
        return taobaoId;
    }

    public void setTaobaoId(String taobaoId) {
        this.taobaoId = taobaoId;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
