package com.synerzip;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnCallAPI, btnDeleteData, btnGetFromDB;

    private RecyclerView recyclerView;
    private EntityAdapter entityAdapter = null;

    private EntityViewModel entityViewModel;

    ProgressDialog progressDialog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCallAPI = findViewById(R.id.btnCallAPI);
        btnDeleteData = findViewById(R.id.btnDeleteData);
        btnGetFromDB = findViewById(R.id.btnGetFromDB);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        entityViewModel = ViewModelProviders.of(this).get(EntityViewModel.class);
        entityViewModel.getallEntities().observe(this, new Observer<List<Entity>>() {
            @Override
            public void onChanged(@Nullable List<Entity> entities) {
                // Update recycler view
                //  Toast.makeText(MainActivity.this, "onChanges",Toast.LENGTH_SHORT).show();
                if (entities.size() > 0) {
                    recyclerView.setAdapter(null);
                    entityAdapter = new EntityAdapter(MainActivity.this);
                    entityAdapter.submitList(entities);
                    recyclerView.setAdapter(entityAdapter);
                }
            }
        });


        btnCallAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()) {
                    entityViewModel.deleteallEntities();
                    recyclerView.setAdapter(null);
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Authenticating Please wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    entityViewModel.callGetEntities().observe(MainActivity.this, new Observer<List<Entity>>() {
                        @Override
                        public void onChanged(List<Entity> entities) {
                            if (entities.size() > 0) {
                                entityAdapter = new EntityAdapter(MainActivity.this);
                                entityAdapter.submitList(entities);
                                recyclerView.setAdapter(entityAdapter);
                            }
                            progressDialog.dismiss();
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this,"No Internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGetFromDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(null);
                entityViewModel.getallEntities().observe(MainActivity.this, new Observer<List<Entity>>() {
                    @Override
                    public void onChanged(@Nullable List<Entity> entities) {
                        // Update recycler view
                        //  Toast.makeText(MainActivity.this, "onChanges",Toast.LENGTH_SHORT).show();
                        if (entities.size() > 0) {
                            entityAdapter = new EntityAdapter(MainActivity.this);
                            entityAdapter.submitList(entities);
                            recyclerView.setAdapter(entityAdapter);
                            Toast.makeText(MainActivity.this,"From Database", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"No data in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entityViewModel.deleteallEntities();
                recyclerView.setAdapter(null);
            }
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
