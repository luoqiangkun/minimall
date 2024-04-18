package org.linlinjava.litemall.db.vo;

import org.linlinjava.litemall.db.domain.LitemallGoodsAttribute;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详细信息
 */
public class GoodsDetailVO implements Serializable {
    private static final long serialVersionUID = 1803853535353L;

    private Integer id;
    private String name;
    private Integer categoryId;
    private Integer brandId;
    private String[] gallery;
    private String keywords;
    private String brief;
    private Boolean isOnSale;
    private String picUrl;
    private String shareUrl;
    private Boolean isNew;
    private Boolean isHot;
    private String unit;
    private BigDecimal counterPrice;
    private BigDecimal retailPrice;
    private String detail;

    private List<LitemallGoodsAttribute> attributeList;

    private List<GoodsSpecificationVO> specificationList;

    private List<LitemallGoodsProduct> productList;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String[] getGallery() {
        return gallery;
    }

    public void setGallery(String[] gallery) {
        this.gallery = gallery;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Boolean getOnSale() {
        return isOnSale;
    }

    public void setOnSale(Boolean onSale) {
        isOnSale = onSale;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(BigDecimal counterPrice) {
        this.counterPrice = counterPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<LitemallGoodsAttribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<LitemallGoodsAttribute> attributeList) {
        this.attributeList = attributeList;
    }

    public List<GoodsSpecificationVO> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<GoodsSpecificationVO> specificationList) {
        this.specificationList = specificationList;
    }

    public List<LitemallGoodsProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<LitemallGoodsProduct> productList) {
        this.productList = productList;
    }
}
