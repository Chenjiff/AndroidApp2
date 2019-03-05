package com.code.chenjifff.experimenttwo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class FoodDAO extends SQLiteOpenHelper {
    private static final String DB_NAME = "food.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "food";
    private SQLiteDatabase defaultWritableDB = null;

    public FoodDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        defaultWritableDB = db;
        String createTableSql = "CREATE TABLE " + TABLE_NAME
                + "(name TEXT PRIMARY KEY, typeSimple TEXT,  type TEXT, nutrient TEXT, backgroundColor INTEGER)";
        db.execSQL(createTableSql);
        insert(new Food("大豆", "粮", "粮食", "蛋白质", "#BB4C3B"));
        insert(new Food("十字花科蔬菜", "蔬", "蔬菜", "维生素C", "#C48D30"));
        insert(new Food("牛奶", "饮", "饮品", "钙", "#4469B0"));
        insert(new Food("海鱼", "肉", "肉食", "蛋白质", "#20A17B"));
        insert(new Food("菌菇类", "蔬", "蔬菜", "微量元素", "#BB4C3B"));
        insert(new Food("番茄", "蔬", "蔬菜", "番茄红素", "#4469B0"));
        insert(new Food("胡萝卜", "蔬", "蔬菜", "胡萝卜素", "#20A17B"));
        insert(new Food("荞麦", "粮", "粮食", "膳食纤维", "#BB4C3B"));
        insert(new Food("鸡蛋", "杂", "杂", "几乎所有营养物质", "#C48D30"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ignore update
    }

    //return the row ID of the last row inserted, if this insert is successful. -1 otherwise.
    public long insert(Food food) {
        SQLiteDatabase db = getWritableDatabase();
        //使用SQLiteStatement:1.增加效率，多条语句可以使用同一缓存。2.防止SQL注入。
        //?视为一个字符串变量，不可以用‘’包括起来，否则不会将其算入待定值
        String insertSql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(insertSql);
        statement.bindString(1, food.getName());
        statement.bindString(2, food.getTypeSimple());
        statement.bindString(3, food.getType());
        statement.bindString(4, food.getNutrient());
        statement.bindLong(5, food.getBackgroundColor());
        long rowID = statement.executeInsert();
        return rowID;
    }

    //return the number of rows affected by this SQL statement execution.
    public int update(Food updateFood, Food oldFood) {
        SQLiteDatabase db = getWritableDatabase();
        String updateSql = "UPDATE " + TABLE_NAME + " SET name = ?, typeSimple = ?, type = ?, " +
                        "nutrient = ?, backgroundColor = ? WHERE name = ?";
        SQLiteStatement statement = db.compileStatement(updateSql);
        statement.bindString(1, updateFood.getName());
        statement.bindString(2, updateFood.getTypeSimple());
        statement.bindString(3, updateFood.getType());
        statement.bindString(4, updateFood.getNutrient());
        statement.bindLong(5, updateFood.getBackgroundColor());
        statement.bindString(6, oldFood.getName());
        int affectedRows = statement.executeUpdateDelete();
        return affectedRows;
    }

    //return the number of rows affected by this SQL statement execution.
    public int delele(Food food) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE name = ?";
        SQLiteStatement statement = db.compileStatement(deleteSql);
        statement.bindString(1, food.getName());
        int affectedRows = statement.executeUpdateDelete();
        return affectedRows;
    }

    public Cursor select(Food food) {
        //SQLiteDatabase db = getReadableDatabase();
        return null;
    }

    public Cursor selectAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //在getWritableDatabase()中会调用onCreate函数，导致在onCreate()中无法使用内部函数
    @Override
    public SQLiteDatabase getWritableDatabase() {
        /*即使自己不调用close()，在每个线程结束后，数据库还是会自己关闭已打开的getWritableDatabase()，
        所以多次点击删除会发现defaultWritableDB == null 一直成立(除非是在同一时间同一线程处理。)*/
        if(defaultWritableDB != null) {
            return defaultWritableDB;
        }
        return super.getWritableDatabase();
    }
}
