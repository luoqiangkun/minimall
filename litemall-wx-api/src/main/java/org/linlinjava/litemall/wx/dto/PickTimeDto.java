package org.linlinjava.litemall.wx.dto;

import java.util.List;

public class PickTimeDto {

    private Integer id;

    private String text;

    private String value;

    private List<PickTimeDto> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<PickTimeDto> getChildren() {
        return children;
    }

    public void setChildren(List<PickTimeDto> children) {
        this.children = children;
    }
}
