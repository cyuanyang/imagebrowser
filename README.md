# imagebrowser
![screenshot](https://github.com/cyuanyang/imagebrowser/blob/master/screenshot/demo.gif)  
image browser for android

# Features

* scale form original location 
* coustom item view by yourself
* coustom item view title view
* Double tap and gesture zoom
* coustom progress bar when loading image

# Usage

Only a code
```Java
 holder.layout.setItemClickListener(new AutoGridLayout.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ///这里holderUri应该传入缩略图的Uri ，但是测试没有缩略图，所以传入的大图。
                //
                // sorry
                YImageBrowser.newInstance().startBrowserImage((Activity) context ,uriStrs, holderUriStrs , view , position);
            }
        });
```

# Support

* use facebook's fresco load web image , so you should invoke Fresco.init() method.





