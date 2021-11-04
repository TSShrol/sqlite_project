package com.rshu.sqliteproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataPiazzaBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="pizza.db";
    private static final int SCHEMA=1;
    //назва таблиць
    public static final String TABLE_PIZZA="pizzas";
    //назви полів
    public static final String СOLUMN_ID="_id";
    public static final String СOLUMN_TITLE="title";
    public static final String СOLUMN_DESCRIPTION="description";
    public static final String СOLUMN_RECIPE="recipe";


    public DataPiazzaBaseHelper(Context context){
        super(context,DATABASE_NAME,null,SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_PIZZA+"( "
                + СOLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + СOLUMN_TITLE+" TEXT, "
                +СOLUMN_DESCRIPTION+" TEXT, "
                +СOLUMN_RECIPE+" TEXT "+
                ");"
        );
        db.execSQL("INSERT INTO "+TABLE_PIZZA+" ( "
                + СOLUMN_TITLE+" , "
                +СOLUMN_DESCRIPTION+" , "
                +СOLUMN_RECIPE +" )"
                + " VALUES ( \""
                +Utiles.PIZZA_1_TITLE+ "\" , \""
                +Utiles.PIZZA_1_DESCRIPTION+"\" , \""
                +Utiles.PIZZA_1_RECIPE+"\" );"
        );
        db.execSQL("INSERT INTO "+TABLE_PIZZA+" ( "
                + СOLUMN_TITLE+" , "
                +СOLUMN_DESCRIPTION+" , "
                +СOLUMN_RECIPE +" )"
                + " VALUES ( \""
                +Utiles.PIZZA_2_TITLE+ "\" , \""
                +Utiles.PIZZA_2_DESCRIPTION+"\" , \""
                +Utiles.PIZZA_2_RECIPE+"\" );"
        );
        Log.d("result","Create DB");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PIZZA);
        onCreate(db);

    }
}
