package com.nailm.mybookliste;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KitabAdapter extends RecyclerView.Adapter<KitabAdapter.Kitabholder> {

    private ArrayList<Kitab>kitablist;
    private Context context;
    private OnItemClickListener listener;


    public KitabAdapter(ArrayList<Kitab> kitablist, Context context) {
        this.kitablist = kitablist;
        this.context = context;
    }

    @NonNull
    @Override
    public Kitabholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.kitabitem,parent,false);//buradaki view doldukdan sonra yeni gorunuwu item olan hisse dolduqdan sonra asagidaki kitabholder(view) icerisine daxil olur ve oradaki itemlerle elaqeli olaraq baglanti qurur.

        return  new Kitabholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Kitabholder holder, int position) {

        Kitab kitab=kitablist.get(position);

        holder.textyaziciadi.setText(kitab.getYaziciadi());
        holder.textkitabadi.setText(kitab.getKitabadi());
        holder.textkitabmezmunu.setText(kitab.getKitabmezmunu());

        holder.kitabresmi.setImageBitmap(kitab.getResmyukle());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                    AlertDialog.Builder builder=new AlertDialog.Builder(context);

                    builder.setCancelable(false);
                    builder.setMessage("Are you sure delete?");

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });


                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SQLiteDatabase database = context.openOrCreateDatabase("Kitablar", MODE_PRIVATE, null);
                            database.execSQL("delete from kitablar WHERE kitabinadi ='"+kitab.getKitabadi()+"'");

                            Intent intent=new Intent(context,MainActivity.class);

                            context.startActivity(intent);


                        }
                    });

                    builder.show();

                    return true;
                }




        });

    }

    @Override
    public int getItemCount() {
        return kitablist.size();
    }

    class Kitabholder extends RecyclerView.ViewHolder {

        private TextView textyaziciadi,textkitabadi,textkitabmezmunu;

        private ImageView kitabresmi;

        public Kitabholder(@NonNull View itemView) {
            super(itemView);

            textyaziciadi=itemView.findViewById(R.id.txtyaziciadi);
            textkitabadi=itemView.findViewById(R.id.txtkitabadi);
            textkitabmezmunu=itemView.findViewById(R.id.txtkitabmezmunu);
            kitabresmi=itemView.findViewById(R.id.heri);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();

                    if(listener!=null && position !=RecyclerView.NO_POSITION){

                        listener.onItemClick(kitablist.get(position),position);


                    }

                }
            });





        }
    }



    public interface OnItemClickListener{

        void onItemClick(Kitab kitab,int position);
    }


    public void setOnItemClicklistener(OnItemClickListener listener){

        this.listener=listener;



    }

}
