package com.cyy.imagebrowserdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    protected RecyclerView recycleView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new MyAdapter();
        recycleView.setAdapter(adapter);
    }
}
