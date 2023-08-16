package com.StokTakip;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class VeriTabaniAtistirmalik extends SQLiteOpenHelper {

    public VeriTabaniAtistirmalik(@Nullable Context context) {
        super(context, "atistirmalik.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE atistirmalik (urunId INTEGER PRIMARY KEY AUTOINCREMENT, urun_adi TEXT, urun_ucreti INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS atistirmalik");
        onCreate(sqLiteDatabase);
    }

}
