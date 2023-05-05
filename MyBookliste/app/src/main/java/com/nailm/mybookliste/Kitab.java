package com.nailm.mybookliste;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.ArrayList;

public class Kitab {
    private String yaziciadi, kitabadi, kitabmezmunu;
    private Bitmap resmyukle;

    public Kitab() {
    }


    public Kitab(String yaziciadi, String kitabadi, String kitabmezmunu, Bitmap resmyukle) {
        this.yaziciadi = yaziciadi;
        this.kitabadi = kitabadi;
        this.kitabmezmunu = kitabmezmunu;
        this.resmyukle = resmyukle;
    }

    public String getYaziciadi() {
        return yaziciadi;
    }

    public void setYaziciadi(String yaziciadi) {
        this.yaziciadi = yaziciadi;
    }

    public String getKitabadi() {
        return kitabadi;
    }

    public void setKitabadi(String kitabadi) {
        this.kitabadi = kitabadi;
    }

    public String getKitabmezmunu() {
        return kitabmezmunu;
    }

    public void setKitabmezmunu(String kitabmezmunu) {
        this.kitabmezmunu = kitabmezmunu;
    }

    public Bitmap getResmyukle() {
        return resmyukle;
    }

    public void setResmyukle(Bitmap resmyukle) {
        this.resmyukle = resmyukle;
    }

    static public ArrayList<Kitab> getdata(Context context) {

        ArrayList<Kitab> kitablist = new ArrayList<Kitab>();

        ArrayList<String> yazaradilist = new ArrayList<>();
        ArrayList<String> kitabadilist = new ArrayList<>();
        ArrayList<String> kitabmezmunulist = new ArrayList<>();
        ArrayList<Bitmap> resmlisti = new ArrayList<>();

        try {

            SQLiteDatabase database = context.openOrCreateDatabase("Kitablar", Context.MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM kitablar", null);

            int yazarindex = cursor.getColumnIndex("yaziciadi");
            int kitabadiindex = cursor.getColumnIndex("kitabinadi");
            int kitabmezmunuindex = cursor.getColumnIndex("kitabinmezmunu");
            int resmindex = cursor.getColumnIndex("kitabresmi");


            while (cursor.moveToNext()) {

                yazaradilist.add(cursor.getString(yazarindex));
                kitabadilist.add(cursor.getString(kitabadiindex));
                kitabmezmunulist.add(cursor.getString(kitabmezmunuindex));

                byte[] resmlistininbyte = cursor.getBlob(resmindex);

                Bitmap abc = BitmapFactory.decodeByteArray(resmlistininbyte, 0, resmlistininbyte.length);
                resmlisti.add(abc);


            }
            cursor.close();


            for (int i = 0; i < yazaradilist.size(); i++) {

                Kitab kitab = new Kitab();
                kitab.setYaziciadi(yazaradilist.get(i));
                kitab.setKitabadi(kitabadilist.get(i));
                kitab.setKitabmezmunu(kitabmezmunulist.get(i));
                kitab.setResmyukle(resmlisti.get(i));


                kitablist.add(kitab);
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

        return kitablist;


    }

}


