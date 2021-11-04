package com.rshu.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PizzaActivity extends AppCompatActivity {
    EditText titlePizza;
    EditText descriptionPizza;
    EditText recipePizza;
    Button saveBtn;
    Button deleteBtn;

    DataPiazzaBaseHelper sqlHelperPizza;
    SQLiteDatabase db;
    Cursor pizzaCursor;
    long pizzaId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        titlePizza=(EditText) findViewById(R.id.title);
        descriptionPizza=(EditText) findViewById(R.id.description);
        recipePizza=(EditText) findViewById(R.id.recipe);

        saveBtn=(Button)findViewById(R.id.saveButton);
        deleteBtn=(Button)findViewById(R.id.deleteButton);

        sqlHelperPizza=new DataPiazzaBaseHelper(this);
        db=sqlHelperPizza.getWritableDatabase();

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            pizzaId=extras.getLong("id");
        }
        if(pizzaId>0){
            String sql1="select * from "+DataPiazzaBaseHelper.TABLE_PIZZA + " where "
                         +DataPiazzaBaseHelper.СOLUMN_ID+"=?";
            pizzaCursor=db.rawQuery(sql1,new String[]{String.valueOf(pizzaId)});
            pizzaCursor.moveToFirst();
            titlePizza.setText(pizzaCursor.getString(1));
            descriptionPizza.setText(pizzaCursor.getString(2));
            recipePizza.setText(pizzaCursor.getString(3));
//            deleteBtn.setVisibility(View.VISIBLE);
        }else{
            deleteBtn.setVisibility(View.GONE); //View.INVISIBLE
        }


    }
    public void save(View view){
        ContentValues cv=new ContentValues();
        cv.put(DataPiazzaBaseHelper.СOLUMN_TITLE,titlePizza.getText().toString());
        cv.put(DataPiazzaBaseHelper.СOLUMN_DESCRIPTION,descriptionPizza.getText().toString());
        cv.put(DataPiazzaBaseHelper.СOLUMN_RECIPE,recipePizza.getText().toString());

        if(pizzaId>0){
            db.update(DataPiazzaBaseHelper.TABLE_PIZZA,cv,DataPiazzaBaseHelper.СOLUMN_ID+"=="+String.valueOf(pizzaId),null);
        }else{
            db.insert(DataPiazzaBaseHelper.TABLE_PIZZA,null,cv);
        }
       goMainActivity();
    }
    public void delete(View view){
        db.delete(DataPiazzaBaseHelper.TABLE_PIZZA,DataPiazzaBaseHelper.СOLUMN_ID+"=?",new String[]{String.valueOf(pizzaId)});
        goMainActivity();
    }

    public void goMainActivity(){
        db.close();
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

}