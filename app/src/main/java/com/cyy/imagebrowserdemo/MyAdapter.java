package com.cyy.imagebrowserdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyy.imagebrowserdemo.mlayout.AutoGridLayout;
import com.cyy.imagebrowserdemo.mlayout.NormalGridLayout;
import com.cyy.yimagebrowser.YImageBrowser;
import com.cyy.yimagebrowser.YImageBrowserActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by cyy on 2016/6/23.
 *
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    protected Context context;
    private List<List<String>> datas;

    public MyAdapter( Context context ,  List<List<String>> datas){
        this.context = context;
        this.datas  = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item , null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final List<String> uriStrs = datas.get(position);
        holder.layout.setHowMuchImageView(uriStrs,true);
        holder.layout.setItemClickListener(new AutoGridLayout.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                YImageBrowser.newInstance().startBrowserImage((Activity) context , uriStrs , view , position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private NormalGridLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = (NormalGridLayout) itemView.findViewById(R.id.autoGridLayout);
        }
    }
}
