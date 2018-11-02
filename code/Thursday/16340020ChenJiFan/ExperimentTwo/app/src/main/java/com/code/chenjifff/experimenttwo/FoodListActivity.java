package com.code.chenjifff.experimenttwo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

public class FoodListActivity extends AppCompatActivity implements View.OnClickListener, FoodListAdapter.OnItemClickListener {
    private RecyclerView rv;
    private ListView lv;
    private FloatingActionButton floatButton;
    private ArrayList<Food> foodData;
    private FoodCollectListViewAdapter collectAdapter;
    private ArrayList<Food> foodCollectData;
    //private int current;
    private static final String STATIC_ACTION = "com.code.chenjifff.experimenttwo.StaticFilter";
    private static final String STATIC_WIDGET_ACTION = "com.code.chenjifff.experimenttwo.StaticWidgetFilter";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        //添加控件到本类
        rv = (RecyclerView) this.findViewById(R.id.food_list);
        lv = (ListView) this.findViewById(R.id.food_collect);
        floatButton = (FloatingActionButton) findViewById(R.id.collect_button);

        //初始化食物列表和收藏列表
        foodData = new ArrayList<Food>();
        foodData.add(new Food("大豆", "粮", "粮食", "蛋白质", "#BB4C3B"));
        foodData.add(new Food("十字花科蔬菜", "蔬", "蔬菜", "维生素C", "#C48D30"));
        foodData.add(new Food("牛奶", "饮", "饮品", "钙", "#4469B0"));
        foodData.add(new Food("海鱼", "肉", "肉食", "蛋白质", "#20A17B"));
        foodData.add(new Food("菌菇类", "蔬", "蔬菜", "微量元素", "#BB4C3B"));
        foodData.add(new Food("番茄", "蔬", "蔬菜", "番茄红素", "#4469B0"));
        foodData.add(new Food("胡萝卜", "蔬", "蔬菜", "胡萝卜素", "#20A17B"));
        foodData.add(new Food("荞麦", "粮", "粮食", "膳食纤维", "#BB4C3B"));
        foodData.add(new Food("鸡蛋", "杂", "杂", "几乎所有营养物质", "#C48D30"));
        foodCollectData = new ArrayList<Food>();
        //处理RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        FoodListAdapter adapter = new FoodListAdapter(this, foodData);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        //处理收藏夹界面的ListView
        collectAdapter = new FoodCollectListViewAdapter(this, foodCollectData);
        lv.setAdapter(collectAdapter);

        //添加各种监听器
        addListener();

        //添加EventBus到食品收藏列表
        EventBus.getDefault().register(this);

        //发送静态广播用于推送
        Intent intentStaticBroadcast = new Intent(STATIC_ACTION);
        Bundle bundleBroadcast = new Bundle();
        Random random = new Random();
        int i = random.nextInt(foodData.size());
        bundleBroadcast.putSerializable("food", foodData.get(i));
        intentStaticBroadcast.putExtras(bundleBroadcast);
        sendBroadcast(intentStaticBroadcast);

        //发送静态Widget广播
        Intent intentStaticWidgetBroadcast = new Intent(STATIC_WIDGET_ACTION);
        Bundle bundleWidget = new Bundle();
        bundleWidget.putSerializable("food", foodData.get(i));
        intentStaticWidgetBroadcast.putExtras(bundleWidget);
        sendBroadcast(intentStaticWidgetBroadcast);
    }

    //添加监听器
    public void addListener() {
        //浮动按钮
        floatButton.setOnClickListener(this);
        //ListView（收藏列表）的点击监听器
        //短按事件（进入详情）
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = foodCollectData.get(position);
                Intent intent = new Intent(FoodListActivity.this, FoodDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", food);
                bundle.putBoolean("ifCollect", true);
                intent.putExtras(bundle);
                //current = position;
                startActivityForResult(intent, 1);
            }
        });
        //长按事件（删除食品）
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Food food = foodCollectData.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(FoodListActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否删除" + food.getName() + "?" );
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        foodCollectData.remove(food);
                        collectAdapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    //食品列表各个项的点击事件
    //短按事件（进入详情）
    @SuppressLint("RestrictedApi")
    @Override
    public void OnItemClick(int i) {
        Food food = foodData.get(i);
        Intent intent = new Intent(this, FoodDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("food", food);
        Boolean ifCollect = foodCollectData.contains(food);
        bundle.putBoolean("ifCollect", ifCollect);
        intent.putExtras(bundle);
        //current = i;
        startActivityForResult(intent, 1);
    }
    //长按事件（删除食品）
    @Override
    public void OnItemLongClick(int i) {
        Food food = foodData.get(i);
        Toast.makeText(this, "删除" + food.getName(), Toast.LENGTH_SHORT).show();
        foodData.remove(food);
        rv.getAdapter().notifyItemRemoved(i);
    }

    //此Activity下控件的点击事件（不包括RecycleView和ListView的子项点击）
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //浮动按钮（切换食品列表界面和收藏界面）
            case R.id.collect_button:
                RelativeLayout collect_bar = (RelativeLayout) findViewById(R.id.collect_bar);
                if(lv.getVisibility() == View.INVISIBLE) {
                    lv.setVisibility(View.VISIBLE);
                    collect_bar.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.INVISIBLE);
                    floatButton.setImageResource(R.mipmap.mainpage);
                }
                else {
                    lv.setVisibility(View.INVISIBLE);
                    collect_bar.setVisibility(View.INVISIBLE);
                    rv.setVisibility(View.VISIBLE);
                    floatButton.setImageResource(R.mipmap.collect);
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
    //点击通知栏回到收藏夹界面
    @Override
    protected void onResume() {
        super.onResume();
        //从intent中获得是否进入收藏夹界面的信息
        Intent intent = getIntent();
        Boolean intoCollect = false;
        if(intent.getExtras() != null) {
            intoCollect = intent.getExtras().getBoolean("intoCollect", false);
            Log.d("2", "onResume: 11");
        }
        if(intoCollect) {
            RelativeLayout collect_bar = (RelativeLayout) findViewById(R.id.collect_bar);
            rv.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.VISIBLE);
            collect_bar.setVisibility(View.VISIBLE);
            floatButton.setImageResource(R.mipmap.mainpage);
        }
    }

    @Override
    protected void onActivityResult(int rc, int resc, Intent intent) {
        setIntent(intent);
        /*
        Log.d("111", "onActivityResult: 11111111111111111111");
        if(intent == null) {
            Log.d("111", "onActivityResult: 222222211111111111");
            RelativeLayout collect_bar = (RelativeLayout) findViewById(R.id.collect_bar);
            rv.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.VISIBLE);
            collect_bar.setVisibility(View.VISIBLE);
            floatButton.setImageResource(R.mipmap.mainpage);
        }
        /*
        boolean ifCollect = intent.getExtras().getBoolean("ifCollect");
        Food food = foodData.get(current);
        if(ifCollect) {
            if(!foodCollectData.contains(food)) {
                foodCollectData.add(food);
                current = 0;
                collectAdapter.notifyDataSetChanged();
            }
        }
        else {
            if(foodCollectData.contains(food)) {
                foodCollectData.remove(food);
                collectAdapter.notifyDataSetChanged();
            }
        }
        */
    }

    //EventBus订阅处理函数
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CollectEvent collectEvent) {
        /*
        Food food = new Food("null", "null","null","null","#000000");
        Iterator iter = foodData.iterator();
        while (iter.hasNext()) {
            Food food_ = (Food)iter.next();
            if(food_.getName().equals(collectEvent.getFood().getName())) {
                food = food_;
            }
        }
        */
        Food food = collectEvent.getFood();
        if(collectEvent.getIfCollect()) {
            if (!foodCollectData.contains(food)) {
                foodCollectData.add(food);
                collectAdapter.notifyDataSetChanged();
            }
        }
        else{
            if(foodCollectData.contains(food)) {
                foodCollectData.remove(food);
                collectAdapter.notifyDataSetChanged();
            }
        }
    }

    //Activity销毁函数，用于EventBus的注销
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus订阅
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
