package com.rshu.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.database.Cursor;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class MainActivity extends AppCompatActivity {
    ListView pizzaList;
    TextView header;
    DataPiazzaBaseHelper dataPiazzaBaseHelper;
    SQLiteDatabase db;
    Cursor pizzaCursor;

    SimpleCursorAdapter pizzaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header=(TextView) findViewById(R.id.header);
        pizzaList=(ListView) findViewById(R.id.pizza_list);
        pizzaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten=new Intent(getApplicationContext(),PizzaActivity.class);
                inten.putExtra("id",id);
                startActivity(inten);
            }
        });
        try {
            dataPiazzaBaseHelper= new DataPiazzaBaseHelper(getApplicationContext());
        } catch (Exception e) {
            Log.d("DatabaseHelper", e.getMessage());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //відкриваємо підключення

        db=dataPiazzaBaseHelper.getReadableDatabase();
        //отримати дані у вигляді курсору

        pizzaCursor=db.rawQuery("SELECT * FROM " + DataPiazzaBaseHelper.TABLE_PIZZA,null);
        String[] headers= new String[]{ DataPiazzaBaseHelper.СOLUMN_TITLE,DataPiazzaBaseHelper.СOLUMN_DESCRIPTION};
        Log.d("result",headers.toString());
        pizzaAdapter=new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,pizzaCursor,headers,
                new int [] {android.R.id.text1,android.R.id.text2},0);
        header.setText("Знайдено елементів: "+String.valueOf(pizzaCursor.getCount()));
        pizzaList.setAdapter(pizzaAdapter);

    }
    public void add(View view){
        Intent inten=new Intent(getApplicationContext(),PizzaActivity.class);
        startActivity(inten);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pizzaCursor.close();
    }


}