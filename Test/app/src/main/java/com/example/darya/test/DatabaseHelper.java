package com.example.darya.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Darya on 04.07.2016.
 */


public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    // имя базы данных
    private static final String DATABASE_NAME = "mydatabase.db";
    // версия базы данных
    private static final int DATABASE_VERSION = 1;
    // имя таблицы
    private static final String DATABASE_TABLE = "coins";
    // названия столбцов
    public static final String TOWN_COLUMN = "town";
    public static final String YEAR_COLUMN = "year";
    public static final String NOMINATION_COLUMN = "nomination";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + TOWN_COLUMN
            + " text not null, " + YEAR_COLUMN + " integer, " + NOMINATION_COLUMN
            + " integer);";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        // Создаём новую таблицу
        onCreate(db);
    }
}
