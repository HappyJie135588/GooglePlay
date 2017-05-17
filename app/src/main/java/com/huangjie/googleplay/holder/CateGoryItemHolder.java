package com.huangjie.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huangjie.googleplay.BaseApplication;
import com.huangjie.googleplay.R;
import com.huangjie.googleplay.bean.CateGoryBean;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.ImageOptionsHelper;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 黄杰 on 2017/5/15.
 */

public class CateGoryItemHolder extends BaseHolder<CateGoryBean.InfosBean> {
    @ViewInject(R.id.item_category_icon_1)
    private ImageView item_category_icon_1;
    @ViewInject(R.id.item_category_icon_2)
    private ImageView item_category_icon_2;
    @ViewInject(R.id.item_category_icon_3)
    private ImageView item_category_icon_3;

    @ViewInject(R.id.item_category_name_1)
    private TextView item_category_name_1;
    @ViewInject(R.id.item_category_name_2)
    private TextView item_category_name_2;
    @ViewInject(R.id.item_category_name_3)
    private TextView item_category_name_3;



    @Override
    protected View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.list_item_category, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    protected void refreshUI(CateGoryBean.InfosBean data) {
        //给View设置数据
        x.image().bind(item_category_icon_1, Constans.ImageUrl + data.getUrl1(), ImageOptionsHelper.getOptions());
        x.image().bind(item_category_icon_2, Constans.ImageUrl + data.getUrl2(), ImageOptionsHelper.getOptions());
        x.image().bind(item_category_icon_3, Constans.ImageUrl + data.getUrl3(), ImageOptionsHelper.getOptions());

        item_category_name_1.setText(data.getName1());
        item_category_name_2.setText(data.getName2());
        item_category_name_3.setText(data.getName3());

    }
}
