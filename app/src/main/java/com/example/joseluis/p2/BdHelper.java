package com.example.joseluis.p2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joseluis on 3/11/16.
 */

public class BdHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="caracteristicas.sqlite";
    private static final int DB_VERSION=1;

    public BdHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataBaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
