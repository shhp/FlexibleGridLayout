/*
 * Copyright 2014 shhp

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.shhp.flexiblegridlayout;

import com.shhp.flexiblegridlayout.FlexibleGridLayout.OnLayoutListener;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SampleActivity extends Activity {

    private FlexibleGridLayout mFlexibleGridLayout;
    private boolean mFirstLayout = true;
    
    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mFlexibleGridLayout != null) {
                Log.i("SampleActivity","id:"+v.getId());
                mFlexibleGridLayout.removeAView(v);
            }
        }
        
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        
        mFlexibleGridLayout = (FlexibleGridLayout) this.findViewById(R.id.flexible_grid_layout);
        mFlexibleGridLayout.setHorizontalMargin(this.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        mFlexibleGridLayout.setVerticalMargin(this.getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));
        mFlexibleGridLayout.setOnLayoutListener(new OnLayoutListener() {

            @Override
            public void onFinishLayout() {
                if (!mFirstLayout)  return;
                
                int i;
                for (i = 1; i <= 3; i++) {
                    TextView shortTextView = new TextView(SampleActivity.this);
                    shortTextView.setTextSize(17);
                    shortTextView.setBackgroundColor(0xff000000);
                    shortTextView.setTextColor(0xffffffff);
                    shortTextView.setText("short"+i);
                    shortTextView.setOnClickListener(mOnClickListener);
                    mFlexibleGridLayout.addAView(shortTextView);
                }
                
                TextView longTextView = new TextView(SampleActivity.this);
                longTextView.setTextSize(17);
                longTextView.setBackgroundColor(0xff000000);
                longTextView.setTextColor(0xffffffff);
                longTextView.setMaxLines(1);
                longTextView.setText("looooooooooooooooooooooooooooong1");
                longTextView.setOnClickListener(mOnClickListener);
                mFlexibleGridLayout.addAView(longTextView);
                
                for (int j = 1; j <= 4; j++) {
                    TextView middleTextView = new TextView(SampleActivity.this);
                    middleTextView.setTextSize(17);
                    middleTextView.setBackgroundColor(0xff000000);
                    middleTextView.setTextColor(0xffffffff);
                    middleTextView.setText("middddddddddle"+j);
                    middleTextView.setMaxLines(1);
                    middleTextView.setOnClickListener(mOnClickListener);
                    mFlexibleGridLayout.addAView(middleTextView);
                }
                mFirstLayout = false;
            }
            
        });
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sample, menu);
        return true;
    }

}
