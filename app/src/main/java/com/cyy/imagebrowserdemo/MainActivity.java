package com.cyy.imagebrowserdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView recycleView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        initView();
    }

    private void initView() {
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        adapter = new MyAdapter(this);
        recycleView.setAdapter(adapter);
    }
}
