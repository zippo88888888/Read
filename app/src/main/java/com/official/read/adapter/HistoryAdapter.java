package com.official.read.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.base.BaseAdapter;
import com.official.read.base.BaseViewHolder;
import com.official.read.content.bean.RecommendBean;
import com.official.read.util.ImageLoader;

import java.util.List;

/**
 * com.official.read.adapter
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class HistoryAdapter extends BaseAdapter<RecommendBean> {

    private FuckSetColorListener fuckSetColorListener;

    public void setFuckSetColorListener(FuckSetColorListener fuckSetColorListener) {
        this.fuckSetColorListener = fuckSetColorListener;
    }

    public HistoryAdapter(Context context, List<RecommendBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutID() {
        return R.layout.new_history_item_layout;
    }

    @Override
    public void onBinderItemHolder(BaseViewHolder holder, int position) {
        ImageView pic = holder.getView(R.id.new_home_list_item_pic); // 图片
        TextView title = holder.getView(R.id.new_home_list_item_title1); // 标题
        TextView state = holder.getView(R.id.new_home_list_item_state1); // 状态
        TextView cjTxt = holder.getView(R.id.new_home_list_item_cjMoneyTxt1); // 成交价的文字说明
        TextView cjMoney = holder.getView(R.id.new_home_list_item_cjMoney1); // 成交价
        TextView time = holder.getView(R.id.new_home_list_item_time1); // 时间
        ImageView timePic = holder.getView(R.id.new_home_list_item_timePic1); // 时间的图片
        RecommendBean item = getItem(position);
        ImageLoader.loadImage(pic, item.img);
        title.setText(item.house_name);
        cjMoney.setText("￥" + item.transaction_price + "万");
        fuckSetColorListener.setColor(cjMoney);
    }

    public interface FuckSetColorListener {
        void setColor(TextView cjMoney);
    }
}
