package org.linlinjava.litemall.db.vo;

import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;

import java.io.Serializable;
import java.util.List;

/**
 * 商品规则
 */
public class GoodsSpecificationVO implements Serializable {

    private static final long serialVersionUID = 402035325269L;

    private String name;
    private List<LitemallGoodsSpecification> valueList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LitemallGoodsSpecification> getValueList() {
        return valueList;
    }

    public void setValueList(List<LitemallGoodsSpecification> valueList) {
        this.valueList = valueList;
    }
}
