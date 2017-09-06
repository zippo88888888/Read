package com.official.read.content.bean;

import java.io.Serializable;
import java.util.List;

/**
 * com.official.read.content.bean
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public class DetailBean implements Serializable {

    public List<HouseInfoImage> imgs;
    public String house_id;
    public String house_name;
    public String numbers;
    public String down_payment;
    public String price;
    public String transaction_price;
    public String reserve_price;
    public String end_time;
    public String deposit;
    public String source;
    public String house_area;
    public String floor_name;
    public String preemption_claimant;
    public String is_rent;
    public String detail_address;
    public String tag;
    public String house_type;
    public String equipment;
    public String towards;
    public String canfield;
    public String limits;
    public String build;
    public String auction_status;

    public String telphone;
    public String auction_7day_count;
    public String auction_total;
    public String auction_des_url;
    public String auction_info_url;
    public String map_address;
    public String is_collect;

    public static class HouseInfoImage {
        public String imgs;
    }
}
