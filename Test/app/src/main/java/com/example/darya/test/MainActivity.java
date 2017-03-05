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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
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
        EditText searchField = (EditText) findViewById(R.id.searchET);
        String town = String.valueOf(searchField.getText());
        if ( town.isEmpty() ) {
            List<CoinModel> coinList = mDatabaseHelper.getAllCoins();
            infoTextView.setText(TextUtils.join(", ", coinList.toArray()));
        }else{
            CoinModel coin = mDatabaseHelper.getCoinByTown(town);
            if (coin != null) {
                infoTextView.setText(coin.toString());
            }else{
                infoTextView.setText(town + ": запись не найдена");
            }
        }
    }

    public void onBtnSaveClick(View v){
        EditText townField = (EditText)findViewById(R.id.townEF);
        EditText yearField = (EditText)findViewById(R.id.yearEF);
        EditText nominationField = (EditText)findViewById(R.id.nominEF);
        mDatabaseHelper.addCoin(
                new CoinModel(String.valueOf(townField.getText()),
                        Integer.valueOf(String.valueOf(yearField.getText())),
                        Integer.valueOf(String.valueOf(nominationField.getText()))));
    }
}
