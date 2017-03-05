package com.example.darya.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darya on 04.07.2016.
 */


public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    // имя базы данных
    private static final String DATABASE_NAME = "mydatabase.db";
    // версия базы данных
    private static final int DATABASE_VERSION = 1;
    // имя таблицы
    public static final String DATABASE_TABLE = "coins";
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

    public void addCoin(CoinModel coin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
        values.put(TOWN_COLUMN, coin.town);
        values.put(YEAR_COLUMN, coin.year);
        values.put(NOMINATION_COLUMN, coin.nomination);
        // Вставляем данные в таблицу
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public List<CoinModel> getAllCoins() {
        List<CoinModel> coinList = new ArrayList<CoinModel>();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CoinModel coin = new CoinModel(
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3))
                );
                coinList.add(coin);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return coinList;
    }

    public CoinModel getCoinByTown(String town) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{BaseColumns._ID,
                        TOWN_COLUMN, YEAR_COLUMN, NOMINATION_COLUMN}, "upper("+TOWN_COLUMN + ")=?",
                new String[]{town.toUpperCase()}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        CoinModel coin = null;
        if (cursor.getCount() != 0) {
            coin = new CoinModel(cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)));
        }
        cursor.close();
        return coin;
    }
}
