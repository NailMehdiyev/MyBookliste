package com.nailm.mybookliste;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private ImageView sekil;
    private TextView yazanad, kitabad, kitabmezmun;
    private Bitmap rc;
    private String yznad, ktad, ktbmzmn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        yazanad = (TextView) findViewById(R.id.detay_yaziciadi);
        kitabad = (TextView)findViewById(R.id.detay_kitabadi);
        kitabmezmun = (TextView)findViewById(R.id.detay_kitabmovzusu);
        sekil = (ImageView) findViewById(R.id.details_img);



        yznad = MainActivity.detailkitab.getYazarad();
        ktad = MainActivity.detailkitab.getKitabad();
        ktbmzmn = MainActivity.detailkitab.getKitabmezmun();
        rc = MainActivity.detailkitab.getKitabresm();



        if(!TextUtils.isEmpty(yznad) && !TextUtils.isEmpty(ktad) &&!TextUtils.isEmpty(ktbmzmn) ){


            yazanad.setText(yznad);

            kitabad.setText(ktad);

            kitabmezmun.setText(ktbmzmn);
            sekil.setImageBitmap(rc);

        }

    }


}