package com.exfinal.exfinal_josuepacherres;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtEmail;
    Button btnRegistrar;
    Spinner spinner1;

    boolean registra = true;
    String id;
    HashMap map = new HashMap();

    Usuario usuario;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asignarReferencias();
        initFirebase();

        obtenerValores();

        String [] opciones = {"Masculino","Femenino"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        spinner1.setAdapter(adapter);

    }

    private void obtenerValores(){
        if (getIntent().hasExtra("pid")){
            registra = false;
            id = getIntent().getStringExtra("pid");
            txtNombres.setText(getIntent().getStringExtra("pnombre"));
            txtApellidos.setText(getIntent().getStringExtra("papellido"));
            txtEmail.setText(getIntent().getStringExtra("pemail"));
        }
    }

    private void initFirebase(){
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void asignarReferencias(){
        txtNombres.findViewById(R.id.txtNombres);
        txtApellidos.findViewById(R.id.txtApellidos);
        txtEmail.findViewById(R.id.txtEmail);
        spinner1.findViewById(R.id.spinner1);
        btnRegistrar.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(v -> {
            capturarDatos();
            String mensaje = "";
            reference.child("Usuario").child(usuario.getId()).setValue(usuario);
            mensaje = "Usuario registrado!";

            AlertDialog.Builder ventana = new AlertDialog.Builder(MainActivity.this);
            ventana.setTitle("Info");
            ventana.setMessage(mensaje);
            ventana.setPositiveButton("Aceptar", (dialogInterface, i) -> {
                finish();
            });
            ventana.create().show();
        });
    }

    private void capturarDatos(){
        String nombres = txtNombres.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String correo = txtEmail.getText().toString();
        String sexo = spinner1.getSelectedItem().toString();

        usuario = new Usuario(UUID.randomUUID().toString(),nombres,apellidos,correo, sexo);
    }
}