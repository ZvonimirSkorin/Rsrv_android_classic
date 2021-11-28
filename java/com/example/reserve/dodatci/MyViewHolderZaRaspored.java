package com.example.reserve.dodatci;



import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reserve.R;

import org.w3c.dom.Text;


class MyViewHolderZaRaspored extends RecyclerView.ViewHolder {
    TextView datum;
    TextView vrijeme;
    ImageView imageView;
    TextView firma;
    TextView adresa;
    View v;

    public MyViewHolderZaRaspored(@NonNull View itemView) {

        super(itemView);
        datum=itemView.findViewById(R.id.ustaskiDatum);
        imageView=itemView.findViewById(R.id.pickasamiotkazatcu);
        vrijeme=itemView.findViewById(R.id.crticarasproedna);
        firma=itemView.findViewById(R.id.bezvezninaslov);
        adresa=itemView.findViewById(R.id.bezveznaadresa);
        v=itemView;

    }
}
