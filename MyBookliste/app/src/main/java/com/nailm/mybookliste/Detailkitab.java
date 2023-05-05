package com.nailm.mybookliste;

import android.graphics.Bitmap;

public class Detailkitab {

    private String yazarad,kitabad,kitabmezmun;
    private Bitmap kitabresm;


    public Detailkitab(String yazarad, String kitabad, String kitabmezmun, Bitmap kitabresm) {
        this.yazarad = yazarad;
        this.kitabad = kitabad;
        this.kitabmezmun = kitabmezmun;
        this.kitabresm = kitabresm;
    }


    public String getYazarad() {
        return yazarad;
    }

    public String getKitabad() {
        return kitabad;
    }

    public String getKitabmezmun() {
        return kitabmezmun;
    }

    public Bitmap getKitabresm() {
        return kitabresm;
    }
}
