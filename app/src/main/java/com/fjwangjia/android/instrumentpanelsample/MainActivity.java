package com.fjwangjia.android.instrumentpanelsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fjwangjia.android.instrumentpanel.Block;
import com.fjwangjia.android.instrumentpanel.InstrumentPanelView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final InstrumentPanelView instrumentPanelView = (InstrumentPanelView) findViewById(R.id.info);
        instrumentPanelView.addBlock(new Block(0xFFDC143C,0.3f));
        instrumentPanelView.addBlock(new Block(0xFFCD4B1C, 0.6f));
        instrumentPanelView.addBlock(new Block(0xFFE3B33F, 0.8f));
        instrumentPanelView.addBlock(new Block(0xFF6B9E22, 1.15f));



//        List<Block> blocks = new ArrayList<>();
//        blocks.add(new Block(0xFFDC143C,0.3f));
//        instrumentPanelView.setBlockList(blocks);

        instrumentPanelView.postDelayed(new Runnable() {
            @Override
            public void run() {
                instrumentPanelView.pointerTo(0.7f);
            }
        }, 1000);
    }
}
