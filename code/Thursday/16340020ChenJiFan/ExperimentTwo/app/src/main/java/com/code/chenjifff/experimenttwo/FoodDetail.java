package com.code.chenjifff.experimenttwo;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class FoodDetail extends AppCompatActivity implements View.OnClickListener {
    private Button back;
    private Button collectBut;
    private boolean ifCollect;
    private Food food;
    private ListView lv;
    private static final String DYNAMIC_ACTION = "con.code.chenjifff.experimenttwo.DynamicReceiver";
    private static final String DYNAMIC_WIDGET_ACTION = "com.code.chenjifff.experimenttwo.DynamicWidgetFilter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        food = new Food("null", "null","null","null","#000000");
        Bundle ex = getIntent().getExtras();
        if(ex != null) {
            food = (Food) ex.getSerializable("food");
            ifCollect = ex.getBoolean("ifCollect");
        }
        TextView name1 = (TextView) findViewById(R.id.food_detail_name1);
        name1.setText(food.getName());
        TextView name2 = (TextView) findViewById(R.id.food_detail_name2);
        name2.setText(food.getName());
        TextView nutrient = (TextView) findViewById(R.id.food_detail_nutrient);
        nutrient.setText("富含" + food.getNutrient());
        FrameLayout bar = (FrameLayout) findViewById(R.id.detail_top_bar);
        bar.setBackgroundColor(food.getBackgroundColor());

        collectBut = (Button) findViewById(R.id.collect_icon);
        collectBut.setTag(0);
        if(ifCollect) {
            collectBut.setBackgroundResource(R.mipmap.full_star);
            collectBut.setTag(1);
        }

        collectBut.setOnClickListener(this);
        back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(this);

        lv = (ListView) findViewById(R.id.detail_list);
        ArrayList<Map<String, Object>> data = new ArrayList();
        String[] items = {"分享信息", "不感兴趣", "查找更多信息", "出错反馈"};
        for(int i = 0; i < 4; i++) {
            Map<String, Object> _data = new LinkedHashMap<String, Object>();
            _data.put("item_name", items[i]);
            data.add(_data);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.detail_list_layout, new String[] {"item_name"}, new int[]{R.id.item});
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back_button:
                //Bundle bundle = new Bundle();
                Intent intent = new Intent();
                //intent.putExtras(bundle);
                setResult(1, intent);
                finish();
                break;
            case R.id.collect_icon:
                CollectEvent collectEvent = new CollectEvent(food, false);
                if((int) collectBut.getTag() == 0) {
                    collectBut.setBackgroundResource(R.mipmap.full_star);
                    collectBut.setTag(1);
                    //ifCollect = true;
                    collectEvent.setIfCollect(true);
                    //注册动态广播用于推送
                    IntentFilter filter = new IntentFilter(DYNAMIC_ACTION);
                    DynamicReceiver dynamicReceiver = new DynamicReceiver();
                    registerReceiver(dynamicReceiver, filter);
                    //发送动态广播用于推送
                    Intent intentDynamicBroadcast = new Intent(DYNAMIC_ACTION);
                    Bundle bundleBroadcast = new Bundle();
                    bundleBroadcast.putSerializable("food", food);
                    intentDynamicBroadcast.putExtras(bundleBroadcast);
                    sendBroadcast(intentDynamicBroadcast);
                    //注销这个动态广播

                    //注册动态广播用于Widget
                    IntentFilter filterWidget = new IntentFilter(DYNAMIC_WIDGET_ACTION);
                    DynamicReceiver receiverWidget = new DynamicReceiver();
                    registerReceiver(receiverWidget, filterWidget);
                    //发送动态广播用于Widget
                    Intent intentWidget = new Intent(DYNAMIC_WIDGET_ACTION);
                    Bundle bundleWidget = new Bundle();
                    bundleWidget.putSerializable("food", food);
                    intentWidget.putExtras(bundleWidget);
                    sendBroadcast(intentWidget);
                    //注销这个广播

                }
                else {
                    collectBut.setBackgroundResource(R.mipmap.empty_star);
                    collectBut.setTag(0);
                    //ifCollect = false;
                }
                EventBus.getDefault().post(collectEvent);
                break;
            default:
                break;
        }
    }

}
