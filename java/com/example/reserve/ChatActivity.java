package com.example.reserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.reserve.Notifications.Client;
import com.example.reserve.Notifications.Data;
import com.example.reserve.Notifications.MyResponse;
import com.example.reserve.Notifications.Sender;
import com.example.reserve.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    ImageButton btn_send;
    FirebaseUser user;
    DatabaseReference reference;
    EditText text_send;
    MessageAdapter messageAdapter;
    ArrayList<chat_class> mchat;
    RecyclerView recyclerView;
    public String korisnik;
   public FirebaseAuth firebaseAuth;
   public FirebaseUser fuser;
   APIService apiService;
   Boolean notify=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        text_send=findViewById(R.id.poruka);
        btn_send=findViewById(R.id.btnSend);
         korisnik= getIntent().getStringExtra("Korisnik");

        recyclerView=findViewById(R.id.Recy);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        readMessage(korisnik,"reserve");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        apiService= Client.getClient("https://fom.googleapis.com/").create(APIService.class);



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify=true;
                String mag=text_send.getText().toString();
                text_send.setText("");
                if(!mag.equals("")){
                    sendMassage(korisnik,"ReserVe",mag);
                }
                else {
                    Toast.makeText(ChatActivity.this,"Ne pumpaj mi memoriju praznim porukama!",Toast.LENGTH_SHORT).show();
                }
            }
        });

  updateToken(FirebaseInstanceId.getInstance().getToken());

    }

    private void updateToken(String token){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token(token);
        reference.child(fuser.getUid()).setValue(token1);



    }


    private void sendMassage(String sender, String receiver, String message){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap =new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message",message);
        reference.child("Chate").child(korisnik).push().setValue(hashMap);

        final String mag=message;
        reference=FirebaseDatabase.getInstance().getReference("Tokens");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(notify){
                sendNotifications(fuser.getUid(),fuser.getEmail(),mag);}
                notify=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void sendNotifications(String receiver, String username, String message){
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Tokens");
        user=FirebaseAuth.getInstance().getCurrentUser();
        Query query=tokens.orderByKey().equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Token token=snapshot.getValue(Token.class);
                Data data=new Data(receiver,R.mipmap.ic_launcher_round,username+": "+message, "Nova poruka", fuser.getUid());
                         if(token.getToken()!=null){
                Sender sender =new Sender(data,token.getToken());
                apiService.sendNotification(sender)
                        .enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                if(response.code()==200)
                                    if(response.body().success==1){
                                   Toast.makeText(ChatActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                                    }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {

                            }
                        });}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void readMessage(String myid, String userid){
        mchat=new ArrayList<>();
       String s= korisnik;

        Toast.makeText(ChatActivity.this, s, Toast.LENGTH_SHORT).show();
        reference=FirebaseDatabase.getInstance().getReference("Chate").child(s);
        if(reference!=null){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    chat_class chat=snapshot1.getValue(chat_class.class);
                    if(chat.getReceiver().equals("ReserVe"))
                    {
                        mchat.add(chat);
                    }
                    messageAdapter=new MessageAdapter(ChatActivity.this,mchat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}



    }

}