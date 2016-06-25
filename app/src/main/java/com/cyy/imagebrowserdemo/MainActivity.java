package com.cyy.imagebrowserdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ///测试数据
    private String testJsonStr = "[\n" +
            "    [\n" +
            "        \"http://pic38.nipic.com/20140226/18071023_170840846000_2.jpg\",\n" +
            "        \"http://f.hiphotos.baidu.com/zhidao/pic/item/5366d0160924ab188229104f33fae6cd7b890b8e.jpg\",\n" +
            "        \"http://www.bz55.com/uploads1/allimg/110217/1_110217174409_5.jpg\",\n" +
            "        \"http://pic11.nipic.com/20101120/3601880_100711609451_2.jpg\",\n" +
            "        \"http://www.bz55.com/uploads/allimg/140927/1-14092G05K4.jpg\",\n" +
            "        \"http://pic38.nipic.com/20140226/18071023_170318899000_2.jpg\",\n" +
            "        \"http://img2.3lian.com/2014/f5/171/d/35.jpg\",\n" +
            "        \"http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1368001227380.jpg\",\n" +
            "        \"http://img15.3lian.com/2015/a1/13/d/17.jpg\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"http://d.hiphotos.baidu.com/zhidao/pic/item/3b87e950352ac65c1b6a0042f9f2b21193138a97.jpg\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"http://pic31.nipic.com/20130629/12199841_153811088000_2.jpg\",\n" +
            "        \"http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1369621315341.jpg\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"http://sc.jb51.net/uploads/allimg/140520/10-140520212515A9.jpg\",\n" +
            "        \"http://img15.3lian.com/2015/a1/13/d/20.jpg\",\n" +
            "        \"http://img2.3lian.com/2014/f4/63/d/14.jpg\",\n" +
            "        \"http://img.taopic.com/uploads/allimg/110920/2443-1109200TK99.jpg\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"http://img3.iqilu.com/data/attachment/forum/201308/22/163057jyrg229qerdeciod.jpg\",\n" +
            "        \"http://pic.6188.com/upload_6188s/flashAll/s800/20120508/1336442181tsUxmr.jpg\",\n" +
            "        \"http://img15.3lian.com/2015/a1/13/d/13.jpg\",\n" +
            "        \"http://img15.3lian.com/2015/a1/13/d/24.jpg\",\n" +
            "        \"http://pic1.nipic.com/2009-02-20/2009220135032130_2.jpg\",\n" +
            "        \"http://img.taopic.com/uploads/allimg/110512/6351-110512095Q923.jpg\",\n" +
            "        \"http://img.taopic.com/uploads/allimg/120910/219077-12091016033980.jpg\"\n" +
            "    ]\n" +
            "]";

    protected RecyclerView recycleView;
    private MyAdapter adapter;

    private List<List<String>> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        getData();
        initView();
    }

    private void initView() {
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        adapter = new MyAdapter(this, datas);
        recycleView.setAdapter(adapter);
    }

    private void getData() {
        List<List<String>> tempArr ;
        tempArr = new Gson().fromJson
                (testJsonStr,  new TypeToken<List<List<String>>>(){}.getType());

        datas.addAll(tempArr);
        datas.addAll(tempArr);
        datas.addAll(tempArr);
        datas.addAll(tempArr);
        datas.addAll(tempArr);
        datas.addAll(tempArr);
        datas.addAll(tempArr);
    }
}
