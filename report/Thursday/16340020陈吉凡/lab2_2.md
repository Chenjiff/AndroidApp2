# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------: | :-------------: | :------------: | :-------------: |
| 年级 | 2016级 | 专业（方向） | 软件工程 |
| 学号 | 16340020 | 姓名 | 陈吉凡 |
| 电话 | 15017239913 | Email | 1004920224@qq.com |
| 开始日期 | 2018.10.25 | 完成日期 | 2018.11.1 |

---

## 一、实验题目
AppWidget 使用

---

## 二、实现内容
* widget初始情况如下：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/192826_99bf526a_2164918.png "week8_begin.PNG")  
* 点击widget可以启动应用，并在widget随机推荐一个食品。  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/192850_6485b167_2164918.png "week8_recommendation.PNG")  
* 点击widget跳转到所推荐食品的详情界面。  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/192919_a44cf72c_2164918.png "week8_jump.PNG")  
* 点击收藏图标，widget相应更新。  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/192945_db62bdf4_2164918.png "week8_update.PNG")  
* 点击widget跳转到收藏列表。  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/193007_87a9da42_2164918.png "week8_collection.PNG")  
* 实现方式要求:启动时的widget更新通过静态广播实现，点击收藏图标时的widget更新通过动态广播实现。


---

## 三、课堂及课后实验结果
### (1)实验截图
* 初始界面：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/193151_dea4eb84_2164918.png "1.png")  
* 点击widget：  
  ![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/194807_053566eb_2164918.png "2.png")  
* 返回查看widget：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/194843_98a0cc33_2164918.png "3.png")  
* 点击进入推荐食品的详情：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/194926_cc7a975c_2164918.png "4.png")  
* 点击收藏并返回查看widget：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/195009_2e37d94d_2164918.png "5.png")  
*点击widget：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1101/195039_735becf7_2164918.png "6.png")


### (2)实验步骤以及关键代码
首先是创建widget，直接左上角找到并创建widget，设置完会发现多了几个文件：
app_widget_info.xml:配置文件，基本不用修改
app_widget.xml:widget布局文件，根据自己所想显示的界面来写布局文件，注意只能使用所给的RelativeLayout，我包含了一个TextView和一个ImageView，较简单就不展示了。
strings.xml文件会多一行关于widget的值，修改可设置widget默认显示的字。
然后是最重要的AppWidget.java:
首先设置点击进入应用：重写onUpdate函数：
```
@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        //设置点击进入应用
        Intent intent = new Intent(context, FoodListActivity.class);
        if(extras != null) {
            intent.putExtras(extras);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
        views.setOnClickPendingIntent(R.id.widget_text, pendingIntent);
        ComponentName name = new ComponentName(context, AppWidget.class);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(name, views);
    }
```
重写onReceive函数，来接收静态广播，记得要在AndroidManifest.xml文件里注册：
```
@Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        if(intent.getAction().equals(STATIC_WIDGET_ACTION)) {
            extras = intent.getExtras();
            if(extras != null) {
                 //对widget做出的修改
            }
        }
    }
```  
注册：
```
<receiver android:name=".AppWidget">
            <intent-filter>
                <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
                <action android:name="com.code.chenjifff.experimentone.StaticWidgetFilter" />
            </intent-filter>
            <meta-data
                android:name="android.appwidget.provider"
                android:resource="@xml/app_widget_info" />
        </receiver>
```
发送静态广播，在onCreate函数里：
```
        Intent intentStaticWidgetBroadcast = new Intent(STATIC_WIDGET_ACTION);
        Bundle bundleWidget = new Bundle();
        bundleWidget.putSerializable("food", foodData.get(i));
        intentStaticWidgetBroadcast.putExtras(bundleWidget);
        sendBroadcast(intentStaticWidgetBroadcast);
```

动态广播的注册和发送在需要的地方加上：
```
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
```
接收到的行为我和上次的动态广播推送写在一起了：
```
else if(intent.getAction().equals(DYNAMIC_WIDGET_ACTION)) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                //修改显示的内容
            }
}
```

### (3)实验遇到的困难以及解决思路
1.修改widget的内容和点击事件，不能想以前的方法一样直接获取到view，而是要通过特定函数获得view，再通过Android提供的接口更新,并且要及时更新点击所使用的pendingIntent。解决：
```
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
                //.....
                views.setTextViewText(R.id.widget_text, "今日推荐 " + food.getName());
                //更改点击所使用的pendingIntent以便进入食品详情界面
                Intent intentClick = new Intent(context, FoodDetail.class);
                intentClick.putExtras(extras);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
                views.setOnClickPendingIntent(R.id.widget_text, pendingIntent);

                ComponentName componentName = new ComponentName(context, AppWidget.class);
                AppWidgetManager.getInstance(context).updateAppWidget(componentName, views);
```
2. 在使用intent传递参数时，开启SingleTask会导致一些问题，即：当Activity未被销毁而通过新的intent进入该Activity时，它的intent（通过getIntent（）获得的）不会自动更新，仍为旧的。解决：通过重写onNewIntent函数设置：
```
@Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
```
另外在没有直接通过intent进入该Activity，而是用finish时返回该Activity时，要在onActivityResult中刷新intent，因为此时不会触发onNewIntent。
```
@Override
    protected void onActivityResult(int rc, int resc, Intent intent) {
        setIntent(intent);
    }
```

---

## 四、实验思考及感想
这次的实验花费了挺多时间，卡在了上述的intent不刷新问题上，当然这次debug也获得了很多activity生命周期和关于intent传递的知识，并且我知道了各个触发事件先后顺序的重要性，希望这次补充可以让我不再在这方面上踩坑。

---

#### 作业要求
* 命名要求：学号_姓名_实验编号，例如12345678_张三_lab1.md
* 实验报告提交格式为md
* 实验内容不允许抄袭，我们要进行代码相似度对比。如发现抄袭，按0分处理