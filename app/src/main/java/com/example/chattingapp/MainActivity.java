package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MesaageModel> arrayList;
    FirebaseAuth auth;
    Calendar c;
    SwipeRefreshLayout swipeRefreshLayout;
    AESEncrypt aesEncrypt = new AESEncrypt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list_of_messages);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        c=Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("d/M/yyyy h:m:s a");
        final String waktu = date.format(c.getTime());
        swipeRefreshLayout=findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayMessages();
            }
        });
        
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(),loginActivity.class));
        } else {
            Toast.makeText(this,
                    "Hello " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getEmail(),
                    Toast.LENGTH_LONG)
                    .show();
            onLoading();
        }

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        auth = FirebaseAuth.getInstance();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new MesaageModel(aesEncrypt.AESEncrypt(input.getText().toString()),FirebaseAuth.getInstance().getCurrentUser().getEmail(), waktu));
                input.setText("");
            }
        });
    }

    private void onLoading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                displayMessages();
            }
        });
    }

    private void displayMessages() {
        recyclerView.setAdapter(null);
        swipeRefreshLayout.setRefreshing(true);
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                  MesaageModel data = dataSnapshot1.getValue(MesaageModel.class);
                    arrayList.add(data);
                }

                AdapterMassage adapterMassage = new AdapterMassage(getApplicationContext(), arrayList);
                recyclerView.setAdapter(adapterMassage);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MainActivity", databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout){
            auth.signOut();
            startActivity(new Intent(getApplicationContext(), loginActivity.class));
        }
        return true;
    }

}
