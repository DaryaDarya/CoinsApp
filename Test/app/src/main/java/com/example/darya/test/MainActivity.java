package com.example.darya.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDatabaseHelper = new DatabaseHelper(this, "mydatabase.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

        /*ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
        values.put(DatabaseHelper.TOWN_COLUMN, "Тихвин");
        values.put(DatabaseHelper.YEAR_COLUMN, "2010");
        values.put(DatabaseHelper.NOMINATION_COLUMN, "10");
        // Вставляем данные в таблицу
        mSqLiteDatabase.insert("coins", null, values);      */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBtnSearchClick(View v){
        TextView infoTextView = (TextView)findViewById(R.id.textView);
        infoTextView.setText( search_2());
    }

    public void onBtnSaveClick(View v){
        EditText townField = (EditText)findViewById(R.id.townEF);
        EditText yearField = (EditText)findViewById(R.id.yearEF);
        EditText nominationField = (EditText)findViewById(R.id.nominEF);
        ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
        values.put(DatabaseHelper.TOWN_COLUMN, String.valueOf(townField.getText()));
        values.put(DatabaseHelper.YEAR_COLUMN, Integer.valueOf(String.valueOf(yearField.getText())));
        values.put(DatabaseHelper.NOMINATION_COLUMN, String.valueOf(nominationField.getText()));
        // Вставляем данные в таблицу
        mSqLiteDatabase.insert("coins", null, values);
    }

    public String search_1(){
        Cursor cursor = mSqLiteDatabase.query("coins", new String[] {DatabaseHelper.TOWN_COLUMN,
                        DatabaseHelper.YEAR_COLUMN, DatabaseHelper.NOMINATION_COLUMN},
                null, null,
                null, null, null) ;

        cursor.moveToFirst();

        String town = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOWN_COLUMN));
        int year = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.YEAR_COLUMN));
        int nomination = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.NOMINATION_COLUMN));

        // не забываем закрывать курсор
        cursor.close();
        return  town + ": " + year + " - " + nomination +" руб";
    }

    public String search_2(){
        // Метод 2: Сырой SQL-запрос
        String query = "SELECT " + DatabaseHelper.TOWN_COLUMN + ", "
                + DatabaseHelper.YEAR_COLUMN  + ", " + DatabaseHelper.NOMINATION_COLUMN
                + " FROM " + DatabaseHelper.DATABASE_TABLE;
        Cursor cursor2 = mSqLiteDatabase.rawQuery(query, null);
        String all = "";
        while (cursor2.moveToNext()) {
            int year = cursor2.getInt(cursor2
                    .getColumnIndex(DatabaseHelper.YEAR_COLUMN));
            int nomination = cursor2.getInt(cursor2
                    .getColumnIndex(DatabaseHelper.NOMINATION_COLUMN));
            String town = cursor2.getString(cursor2
                    .getColumnIndex(DatabaseHelper.TOWN_COLUMN));
            all += "Монета города " + town + ": " + year + " - " + nomination +" руб ";
        }
        cursor2.close();
        return all;
    }
}
