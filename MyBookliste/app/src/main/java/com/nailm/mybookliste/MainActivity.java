package com.nailm.mybookliste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KitabAdapter madapter;
    static public Detailkitab detailkitab;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_ted) {

            Intent intent = new Intent(MainActivity.this, Kitabadd.class);

          //  finish();

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.mrecycler);


        recyclerView.setHasFixedSize(true);

        GridLayoutManager LayoutManager=new GridLayoutManager(this,1);//burada
        recyclerView.addItemDecoration(new Grid());


        recyclerView.setLayoutManager(LayoutManager);





        madapter = new KitabAdapter(Kitab.getdata(this), this);


        recyclerView.setAdapter(madapter);



        madapter.setOnItemClicklistener(new KitabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Kitab kitab, int position) {

                detailkitab=new Detailkitab(kitab.getYaziciadi(), kitab.getKitabadi(), kitab.getKitabmezmunu(),kitab.getResmyukle());

                Intent detail = new Intent(MainActivity.this, DetailsActivity.class);

                    startActivity(detail);





            }
        });










    }

    private class Grid extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

            outRect.bottom=10;

        }
    }
}