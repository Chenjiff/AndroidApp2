package com.code.chenjifff.experimenttwo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.code.chenjifff.experimentone.R;

public class StaticReceiver extends BroadcastReceiver {
    private static final String STATIC_ACTION = "com.code.chenjifff.experimentone.StaticFilter";
    //下面的方法为接收到广播后执行的动作
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(STATIC_ACTION)){
            Bundle bundle = intent.getExtras();
            Food food = (Food)bundle.getSerializable("food");
            //创建PendingIntent以便点击导向食品详情
            Intent foodIntent = new Intent(context, FoodDetail.class);
            foodIntent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, foodIntent, 0);
            //发送通知消息
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("今日推荐")
                    .setContentText(food.getName())
                    .setTicker("你有一条新消息")
                    .setSmallIcon(R.mipmap.empty_star)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
            Notification notification = builder.build();
            notificationManager.notify(0, notification);
        }
    }
}
