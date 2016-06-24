package com.cyy.imagebrowserdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyy.imagebrowserdemo.mlayout.AutoGridLayout;
import com.cyy.imagebrowserdemo.mlayout.NormalGridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyy on 2016/6/23.
 *
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    protected Context context;

    public MyAdapter( Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item , null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<String > uriStrs = new ArrayList<>();
        for (int i = 0 ; i < 7 ; i++)uriStrs.add("http://img2.imgtn.bdimg.com/it/u=451523955,1115502761&fm=21&gp=0.jpg");
        holder.layout.setHowMuchImageView(uriStrs);
    }

    @Override
    public int getItemCount() {
        return 20;
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
