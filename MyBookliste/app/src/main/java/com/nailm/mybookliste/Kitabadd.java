package com.nailm.mybookliste;

import static android.content.Context.MODE_PRIVATE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Statement;

public class Kitabadd extends AppCompatActivity {

    private ImageView resmartir;
    private EditText edityazaradi, editkitabadi, editkitabmezmunu;
    private int icazealmaqucunkod = 0, icazealandansonra = 1;
    private ActivityResultLauncher<String> startactivity;
    private Bitmap secilendeyer, kicildilenresm, yenibitmap;
    private Button qeydiyyat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitabadd);
        resmartir = (ImageView) findViewById(R.id.imageview);
        edityazaradi = (EditText) findViewById(R.id.yaziciadi);
        editkitabadi = (EditText) findViewById(R.id.kitabadi);
        editkitabmezmunu = (EditText) findViewById(R.id.kitabmezmunu);
        qeydiyyat = (Button) findViewById(R.id.qeydiyyat);


       /* startactivity=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                resmartir.setImageURI(result);
            }
        });

        */
    }




    public void resmideyis(View view) {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, icazealmaqucunkod);

        } else {
            Intent resmial = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(resmial, icazealandansonra);

            //  String s = Environment.getExternalStorageDirectory() + "/Download/image.jpg";

            // File dokument = new File(s);


            //    secilendeyer = BitmapFactory.decodeFile(s);

            //  resmartir.setImageBitmap(secilendeyer);

            // ContentValues contentResolver = new ContentValues();


        }


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == icazealmaqucunkod) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent resmial = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(resmial, icazealandansonra);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == icazealandansonra) {
            if (resultCode == RESULT_OK && data != null) {
                Uri resmuri = data.getData();


                try {
                    if (Build.VERSION.SDK_INT >=28) {
                        ImageDecoder.Source sourcedekoder = ImageDecoder.createSource(this.getContentResolver(), resmuri);
                        secilendeyer = ImageDecoder.decodeBitmap(sourcedekoder);
                        resmartir.setImageBitmap(secilendeyer);

                    } else {
                       secilendeyer = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resmuri);
                       resmartir.setImageBitmap(secilendeyer);

                    }

                    qeydiyyat.setEnabled(true);


                } catch (IOException e) {
                    e.printStackTrace();

                }


            }


        }
        super.onActivityResult(requestCode, resultCode, data);

    }




         /*   if (Build.VERSION.SDK_INT >= 28) {

                                      ImageDecoder.Source dekode = ImageDecoder.createSource(Kitabadd.this.getContentResolver(), resm);
                                      secilendeyer = ImageDecoder.decodeBitmap(dekode);
                                      resmartir.setImageBitmap(secilendeyer);



                                  } else {




    //   secilendeyer = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resm);

    // resmartir.setImageBitmap(secilendeyer);


                                  }

                                */


    private Bitmap resmikicilt(Bitmap img) {

            return Bitmap.createScaledBitmap(img, 120, 140, true);

        }



    private void melumatlarisil() {

        edityazaradi.setText("");
        editkitabadi.setText("");
        editkitabmezmunu.setText("");
        yenibitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.resm);
        resmartir.setImageBitmap(yenibitmap);


        qeydiyyat.setEnabled(false);
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(this,MainActivity.class);

        finish();

        startActivity(intent);

            }

    public void registr(View view) {
        String yazaradi = edityazaradi.getText().toString();
        String kitabadi = editkitabadi.getText().toString();
        String kitabmezmunu = editkitabmezmunu.getText().toString();

        if (!TextUtils.isEmpty(yazaradi)) {
            if (!TextUtils.isEmpty(kitabadi)) {
                if (!TextUtils.isEmpty(kitabmezmunu)) {


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    kicildilenresm = resmikicilt(secilendeyer);
                    kicildilenresm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteresm = stream.toByteArray();


                    try {

                        SQLiteDatabase database = this.openOrCreateDatabase("Kitablar", MODE_PRIVATE, null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS kitablar(id INTEGER PRIMARY KEY ,yaziciadi VARCHAR,kitabinadi VARCHAR,kitabinmezmunu VARCHAR,kitabresmi BLOB)");
                        String sql = "INSERT INTO kitablar(yaziciadi,kitabinadi,kitabinmezmunu,kitabresmi) VALUES (?,?,?,?)";

                        SQLiteStatement statement = database.compileStatement(sql);
                        statement.bindString(1, yazaradi);
                        statement.bindString(2, kitabadi);
                        statement.bindString(3, kitabmezmunu);
                        statement.bindBlob(4, byteresm);
                        statement.execute();


                        melumatlarisil();

                        Toast.makeText(getApplicationContext(), "Registr is succesful", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(Kitabadd.this,MainActivity.class);

                        startActivity(intent);



                    } catch (Exception e) {

                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else
                    Toast.makeText(getApplicationContext(), "kitabmezmunu can not empty ", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "kitabadi can not empty", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "yazaradi  can not empty", Toast.LENGTH_SHORT).show();

    }
}