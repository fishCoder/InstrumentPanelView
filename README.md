### 仪表盘 InstrumentPanel

项目设计稿需要做个有高光效果的仪表盘控件，网上也没有找到有高光效果的控件，于是只能自己做一个

```
<com.fjwangjia.android.instrumentpanel.InstrumentPanelView
        android:id="@+id/info"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:background="#ececec"
        app:textColor="#555"
        app:textSize="12dp"/>


instrumentPanelView.addBlock(new Block(0xFFDC143C,0.3f));

or 

List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0xFFDC143C,0.3f));
        instrumentPanelView.setBlockList(blocks);


instrumentPanelView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    instrumentPanelView.pointerTo(0.6f);
                }
            }, 1000);

```


