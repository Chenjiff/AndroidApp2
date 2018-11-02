package com.code.chenjifff.experimenttwo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.code.chenjifff.experimentone.R;

public class DynamicReceiver extends BroadcastReceiver {
    private final String DYNAMIC_ACTION = "con.code.chenjifff.experimentone.DynamicReceiver";
    private static final String DYNAMIC_WIDGET_ACTION = "com.code.chenjifff.experimentone.DynamicWidgetFilter";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        //动态广播的接收器，用于推送
        if(intent.getAction().equals(DYNAMIC_ACTION)){
            Bundle extras = intent.getExtras();
            //发送通知消息
            if(extras != null) {
                Food food = (Food) extras.getSerializable("food");
                //创建PendingIntent以便点击导向食品收藏列表
                Intent foodIntent = new Intent(context, FoodListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("intoCollect", true);
                foodIntent.putExtras(bundle);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, foodIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //发送通知消息
                Notification.Builder builder = new Notification.Builder(context);
                builder.setContentTitle("已收藏")
                        .setContentText(food.getName())
                        .setTicker("你有一条新消息")
                        .setSmallIcon(R.mipmap.full_star)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                Notification notification = builder.build();
                notificationManager.notify(0, notification);
            }
        }
        //动态广播的接收器，用于Widget
        else if(intent.getAction().equals(DYNAMIC_WIDGET_ACTION)) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                //修改显示的文字
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
                Food food = (Food) extras.getSerializable("food");
                views.setTextViewText(R.id.widget_text, "已收藏 " + food.getName());
                //修改点击所使用的pendingIntent以便进入收藏夹界面
                Intent intentClick = new Intent(context, FoodListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("intoCollect", true);
                intentClick.putExtras(bundle);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
                views.setOnClickPendingIntent(R.id.widget_text, pendingIntent);

                ComponentName name = new ComponentName(context, AppWidget.class);
                AppWidgetManager.getInstance(context).updateAppWidget(name, views);
            }

        }
        else {}
    }
}
