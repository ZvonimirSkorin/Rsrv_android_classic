package com.example.reserve;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter <MessageAdapter.MyViewHolder> {

    Context context;
    public static final int MSG_type_LEFT=0;
    public static final int MSG_type_RIGHT=1;
    ArrayList<chat_class> chat_class;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;



    public MessageAdapter(Context context, ArrayList<chat_class> chat_class){
        this.context=context;
        this.chat_class=chat_class;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType==MSG_type_RIGHT){

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false));}
        else
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false));

    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        chat_class chat =chat_class.get(position);

        myViewHolder.show_message.setText(chat.getMessage());


    }


    @Override
    public int getItemCount() {
        return chat_class.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView show_message;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);



        }}


    @Override
    public int getItemViewType(int position) {



        firebaseAuth=FirebaseAuth.getInstance();
        String korisnik = firebaseAuth.getCurrentUser().getUid();
        if (chat_class.get(position).getSender().equals(korisnik)) {

        return MSG_type_RIGHT;
        }
        else
            return MSG_type_LEFT;
    }
}
