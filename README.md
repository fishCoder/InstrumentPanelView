### 仪表盘 InstrumentPanel


`
        <com.fjwangjia.android.instrumentpanel.InstrumentPanelView
                android:id="@+id/info"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:background="#ececec"
                app:textColor="#555"
                app:textSize="12dp"
                android:text="Hello World!" />
        
        
        final InstrumentPanelView instrumentPanelView = (InstrumentPanelView) findViewById(R.id.info);
                instrumentPanelView.addBlock(new Block(0xFFDC143C,0.3f));
                instrumentPanelView.addBlock(new Block(0xFFCD4B1C,0.6f));
                instrumentPanelView.addBlock(new Block(0xFFE3B33F,0.8f));
                instrumentPanelView.addBlock(new Block(0xFF6B9E22,1f));
        
                instrumentPanelView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        instrumentPanelView.pointerTo(0.6f);
                    }
                }, 1000);
`

