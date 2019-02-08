package com.nyi.yureport.vos;

import java.util.List;

/**
 * Created by IN-3442 on 21-Oct-16.
 */

public class ShopVO {

    private String shopID;
    private String name;
    private String imageLink;
    private String place;
    private List<String> type;

    public ShopVO() {
    }

    public ShopVO(String shopID, String name, String imageLink) {
        this.shopID = shopID;
        this.name = name;
        this.imageLink = imageLink;
    }

    public ShopVO(String shopID, String name, String imageLink, String place, List<String> type) {
        this.shopID = shopID;
        this.name = name;
        this.imageLink = imageLink;
        this.place = place;
        this.type = type;
    }

    public ShopVO(String name) {
        this.name = name;
    }

    public String getShopID() {
        return shopID;
    }

    public String getName() {
        return name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getPlace() {
        return place;
    }

    public List<String> getType() {
        return type;
    }
}
