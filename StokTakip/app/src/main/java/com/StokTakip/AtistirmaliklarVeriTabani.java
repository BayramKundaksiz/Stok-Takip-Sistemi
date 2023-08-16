package com.StokTakip;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class AtistirmaliklarVeriTabani {

    public ArrayList<UrunlerModel> tumUrunler(VeriTabaniAtistirmalik vt){

        ArrayList<UrunlerModel> urunlerModelArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM atistirmalik",null);

        while (c.moveToNext()){
            @SuppressLint("Range")

            UrunlerModel y = new UrunlerModel(
                    c.getInt(c.getColumnIndex("urunId")),
                    c.getString(c.getColumnIndex("urun_adi"))
                    ,c.getInt(c.getColumnIndex("urun_ucreti")));
            urunlerModelArrayList.add(y);
        }

        db.close();
        return urunlerModelArrayList;
    }

    public void urunSil(VeriTabaniAtistirmalik vt, int urunId){
        SQLiteDatabase db = vt.getWritableDatabase();
        db.delete("atistirmalik","urunId=?",new String[]{String.valueOf(urunId)});
        db.close();
    }

    public void urunEkle(VeriTabaniAtistirmalik vt, String urun_adi, int urun_ucreti){

        SQLiteDatabase db = vt.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("urun_adi",urun_adi);

        values.put("urun_ucreti",urun_ucreti);

        db.insertOrThrow("atistirmalik",null,values);

        db.close();

    }


    public void urunGuncelle(VeriTabaniAtistirmalik vt, int urunId, String urun_adi, int urun_ucreti){

        SQLiteDatabase db = vt.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("urun_adi",urun_adi);
        values.put("urun_ucreti",urun_ucreti);

        db.update("atistirmalik",values,"urunId=?",new String[]{String.valueOf(urunId)});

        db.close();

    }

    public void urunAzalt(VeriTabaniAtistirmalik vt, int urunId, int urun_ucreti){

        SQLiteDatabase db = vt.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("urun_ucreti",urun_ucreti);
        db.update("atistirmalik",values,"urunId=?",new String[]{String.valueOf(urunId)});

        db.close();
    }

    public void urunArttir(VeriTabaniAtistirmalik vt, int urunId, int urun_ucreti){

        SQLiteDatabase db = vt.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("urun_ucreti",urun_ucreti);
        db.update("atistirmalik",values,"urunId=?",new String[]{String.valueOf(urunId)});

        db.close();
    }
}
