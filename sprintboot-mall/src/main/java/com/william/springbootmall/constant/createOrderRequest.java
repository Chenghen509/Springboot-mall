package com.william.springbootmall.constant;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class createOrderRequest {

    @NotEmpty
    public List<buyItem> buyItemList;

    public List<buyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<buyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
