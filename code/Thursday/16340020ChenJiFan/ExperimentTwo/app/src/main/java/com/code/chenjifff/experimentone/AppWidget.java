package com.code.chenjifff.experimenttwo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.code.chenjifff.experimentone.R;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    private static final String STATIC_WIDGET_ACTION = "com.code.chenjifff.experimentone.StaticWidgetFilter";
    private Bundle extras;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.widget_text, widgetText);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

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

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        if(intent.getAction().equals(STATIC_WIDGET_ACTION)) {
            extras = intent.getExtras();
            if(extras != null) {
                //修改显示的文字
                Food food = (Food) extras.getSerializable("food");
                views.setTextViewText(R.id.widget_text, "今日推荐 " + food.getName());
                //更改点击所使用的pendingIntent以便进入食品详情界面
                Intent intentClick = new Intent(context, FoodDetail.class);
                intentClick.putExtras(extras);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
                views.setOnClickPendingIntent(R.id.widget_text, pendingIntent);

                ComponentName componentName = new ComponentName(context, AppWidget.class);
                AppWidgetManager.getInstance(context).updateAppWidget(componentName, views);
            }
        }
    }
}

