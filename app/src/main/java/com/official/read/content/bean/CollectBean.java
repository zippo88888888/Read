package com.official.read.content.bean;

import com.official.read.content.Content;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.official.read.content.bean
 * Created by ZP on 2017/8/4.
 * description: 收藏
 * version: 1.0
 */

public class CollectBean extends DataSupport implements Serializable {

    public String imgURL;
    public String title;
    public String fID;
    public Date date;

    public String getDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } catch (Exception e) {
            if (!Content.IS_OFFICIAL) {
                e.printStackTrace();
            }
            return "";
        }
    }

}
