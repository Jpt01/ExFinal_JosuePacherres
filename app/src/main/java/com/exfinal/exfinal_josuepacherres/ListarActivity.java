package com.exfinal.exfinal_josuepacherres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListarActivity extends AppCompatActivity {

    RecyclerView rvProductos;
    FloatingActionButton btnAdd;

    FirebaseDatabase database;
    DatabaseReference reference;
    private List<Producto> listaProd = new ArrayList<>();
    AdaptadorPersonalizado adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        asignarReferencias();
        inicializarFirebase();
        mostrarDatos();
    }

    private void mostrarDatos(){
        reference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProd.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    Producto p = item.getValue(Producto.class);
                    listaProd.add(p);
                }
                adaptador = new AdaptadorPersonalizado(ListarActivity.this, listaProd);
                rvProductos.setAdapter(adaptador);
                rvProductos.setLayoutManager(new LinearLayoutManager(ListarActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void  inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
    }

    private void asignarReferencias(){
        rvProductos = findViewById(R.id.rvProductos);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(ListarActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

}