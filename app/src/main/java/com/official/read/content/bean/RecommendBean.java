package com.official.read.content.bean;

import java.io.Serializable;
import java.util.List;

/**
 * com.official.read.content.bean
 * Created by ZP on 2017/7/31.
 * description:
 * version: 1.0
 */

public class RecommendBean implements Serializable {

    public final static int HOUSE_STATUS_DOWNTEMPO = 1;
    public final static int HOUSE_STATUS_ABORTIVE_AUCTION = 2;
    public final static int HOUSE_STATUS_BACKOUT = 3;
    public final static int HOUSE_STATUS_STARTING = 4;
    public final static int HOUSE_STATUS_ABOUNT_TO_BEGIN = 5;
    public final static int HOUSE_STATUS_KNOCKDOWN = 6;

    public String id;
    public String img;
    public String house_name;

    public String down_payment;
    public String price;
    public String transaction_price;
    public String reserve_price;

    public String end_time;
    public String auction_status;
    public String browse;
    public String participants_number;
    public String numbers;
    public String building_type;

    public String tag;
    public String transaction_time;
    public String end_times;

}
