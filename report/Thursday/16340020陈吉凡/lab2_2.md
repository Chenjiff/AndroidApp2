# ��ɽ��ѧ���ݿ�ѧ������ѧԺ������ʵ�鱨��
## ��2018���＾ѧ�ڣ�
| �γ����� | �ֻ�ƽ̨Ӧ�ÿ��� | �ο���ʦ | ֣��� |
| :------: | :-------------: | :------------: | :-------------: |
| �꼶 | 2016�� | רҵ������ | ������� |
| ѧ�� | 16340020 | ���� | �¼��� |
| �绰 | 15017239913 | Email | 1004920224@qq.com |
| ��ʼ���� | 2018.10.25 | ������� | 2018.11.1 |

---

## һ��ʵ����Ŀ
AppWidget ʹ��

---

## ����ʵ������
* widget��ʼ������£�  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/192826_99bf526a_2164918.png "week8_begin.PNG")  
* ���widget��������Ӧ�ã�����widget����Ƽ�һ��ʳƷ��  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/192850_6485b167_2164918.png "week8_recommendation.PNG")  
* ���widget��ת�����Ƽ�ʳƷ��������档  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/192919_a44cf72c_2164918.png "week8_jump.PNG")  
* ����ղ�ͼ�꣬widget��Ӧ���¡�  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/192945_db62bdf4_2164918.png "week8_update.PNG")  
* ���widget��ת���ղ��б�  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/193007_87a9da42_2164918.png "week8_collection.PNG")  
* ʵ�ַ�ʽҪ��:����ʱ��widget����ͨ����̬�㲥ʵ�֣�����ղ�ͼ��ʱ��widget����ͨ����̬�㲥ʵ�֡�


---

## �������ü��κ�ʵ����
### (1)ʵ���ͼ
* ��ʼ���棺  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/193151_dea4eb84_2164918.png "1.png")  
* ���widget��  
  ![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/194807_053566eb_2164918.png "2.png")  
* ���ز鿴widget��  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/194843_98a0cc33_2164918.png "3.png")  
* ��������Ƽ�ʳƷ�����飺  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/194926_cc7a975c_2164918.png "4.png")  
* ����ղز����ز鿴widget��  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/195009_2e37d94d_2164918.png "5.png")  
*���widget��  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1101/195039_735becf7_2164918.png "6.png")


### (2)ʵ�鲽���Լ��ؼ�����
�����Ǵ���widget��ֱ�����Ͻ��ҵ�������widget��������ᷢ�ֶ��˼����ļ���
app_widget_info.xml:�����ļ������������޸�
app_widget.xml:widget�����ļ��������Լ�������ʾ�Ľ�����д�����ļ���ע��ֻ��ʹ��������RelativeLayout���Ұ�����һ��TextView��һ��ImageView���ϼ򵥾Ͳ�չʾ�ˡ�
strings.xml�ļ����һ�й���widget��ֵ���޸Ŀ�����widgetĬ����ʾ���֡�
Ȼ��������Ҫ��AppWidget.java:
�������õ������Ӧ�ã���дonUpdate������
```
@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        //���õ������Ӧ��
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
��дonReceive�����������վ�̬�㲥���ǵ�Ҫ��AndroidManifest.xml�ļ���ע�᣺
```
@Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        if(intent.getAction().equals(STATIC_WIDGET_ACTION)) {
            extras = intent.getExtras();
            if(extras != null) {
                 //��widget�������޸�
            }
        }
    }
```  
ע�᣺
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
���;�̬�㲥����onCreate�����
```
        Intent intentStaticWidgetBroadcast = new Intent(STATIC_WIDGET_ACTION);
        Bundle bundleWidget = new Bundle();
        bundleWidget.putSerializable("food", foodData.get(i));
        intentStaticWidgetBroadcast.putExtras(bundleWidget);
        sendBroadcast(intentStaticWidgetBroadcast);
```

��̬�㲥��ע��ͷ�������Ҫ�ĵط����ϣ�
```
 //ע�ᶯ̬�㲥����Widget
                    IntentFilter filterWidget = new IntentFilter(DYNAMIC_WIDGET_ACTION);
                    DynamicReceiver receiverWidget = new DynamicReceiver();
                    registerReceiver(receiverWidget, filterWidget);
                    //���Ͷ�̬�㲥����Widget
                    Intent intentWidget = new Intent(DYNAMIC_WIDGET_ACTION);
                    Bundle bundleWidget = new Bundle();
                    bundleWidget.putSerializable("food", food);
                    intentWidget.putExtras(bundleWidget);
                    sendBroadcast(intentWidget);
                    //ע������㲥
```
���յ�����Ϊ�Һ��ϴεĶ�̬�㲥����д��һ���ˣ�
```
else if(intent.getAction().equals(DYNAMIC_WIDGET_ACTION)) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                //�޸���ʾ������
            }
}
```

### (3)ʵ�������������Լ����˼·
1.�޸�widget�����ݺ͵���¼�����������ǰ�ķ���һ��ֱ�ӻ�ȡ��view������Ҫͨ���ض��������view����ͨ��Android�ṩ�Ľӿڸ���,����Ҫ��ʱ���µ����ʹ�õ�pendingIntent�������
```
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
                //.....
                views.setTextViewText(R.id.widget_text, "�����Ƽ� " + food.getName());
                //���ĵ����ʹ�õ�pendingIntent�Ա����ʳƷ�������
                Intent intentClick = new Intent(context, FoodDetail.class);
                intentClick.putExtras(extras);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
                views.setOnClickPendingIntent(R.id.widget_text, pendingIntent);

                ComponentName componentName = new ComponentName(context, AppWidget.class);
                AppWidgetManager.getInstance(context).updateAppWidget(componentName, views);
```
2. ��ʹ��intent���ݲ���ʱ������SingleTask�ᵼ��һЩ���⣬������Activityδ�����ٶ�ͨ���µ�intent�����Activityʱ������intent��ͨ��getIntent������õģ������Զ����£���Ϊ�ɵġ������ͨ����дonNewIntent�������ã�
```
@Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
```
������û��ֱ��ͨ��intent�����Activity��������finishʱ���ظ�Activityʱ��Ҫ��onActivityResult��ˢ��intent����Ϊ��ʱ���ᴥ��onNewIntent��
```
@Override
    protected void onActivityResult(int rc, int resc, Intent intent) {
        setIntent(intent);
    }
```

---

## �ġ�ʵ��˼��������
��ε�ʵ�黨����ͦ��ʱ�䣬������������intent��ˢ�������ϣ���Ȼ���debugҲ����˺ܶ�activity�������ں͹���intent���ݵ�֪ʶ��������֪���˸��������¼��Ⱥ�˳�����Ҫ�ԣ�ϣ����β���������Ҳ������ⷽ���ϲȿӡ�

---

#### ��ҵҪ��
* ����Ҫ��ѧ��_����_ʵ���ţ�����12345678_����_lab1.md
* ʵ�鱨���ύ��ʽΪmd
* ʵ�����ݲ�����Ϯ������Ҫ���д������ƶȶԱȡ��緢�ֳ�Ϯ����0�ִ���