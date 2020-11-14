package com.example.crud_android_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.crud_android_firebase.model.Persona;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    public static EditText txt_nombre, txt_correo, txt_apellidos, txt_password;
    public static ListView listView_personas;
    public static Button btn_agregar, btn_eliminar, btn_guardar;
    private List<Persona> listPersona = new ArrayList<Persona>();
    ArrayAdapter<Persona> personaArrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Persona personaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarFirebase();
        enlace();
        actions();
        listarDatos();
        seleccionarDatos();
    }

    private void seleccionarDatos(){
        listView_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                personaSelected = (Persona) parent.getItemAtPosition(position);

                txt_nombre.setText(personaSelected.getNombre());
                txt_apellidos.setText(personaSelected.getApellidos());
                txt_correo.setText(personaSelected.getCorreo());
                txt_password.setText(personaSelected.getPassword());

            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPersona.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()){
                    Persona persona = objSnapshot.getValue(Persona.class);
                    listPersona.add(persona);

                    personaArrayAdapter = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, listPersona);
                    listView_personas.setAdapter(personaArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true); Carga datos sin necesidad de estar conectado a internet, cuando detecta conexion los sube (En apps sencillas)
        databaseReference = firebaseDatabase.getReference();
    }

    public void enlace(){
        txt_nombre = findViewById(R.id.txt_nombrePersona);
        txt_apellidos = findViewById(R.id.txt_apellidosPersona);
        txt_password = findViewById(R.id.txt_passwordPersona);
        txt_correo = findViewById(R.id.txt_correoPersona);

        btn_agregar = findViewById(R.id.btn_agregar);
        btn_eliminar = findViewById(R.id.btn_eliminar);
        btn_guardar  = findViewById(R.id.btn_guardar);

        listView_personas = findViewById(R.id.lv_datosPersonas);
    }

    private void actions(){


        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_nombre.getText().toString().equals("") ||
                        txt_correo.getText().toString().equals("") ||
                        txt_password.getText().toString().equals("")){
                    validacion();
                    Toast.makeText(getApplicationContext(), "Rellene los campos obligatorios",Toast.LENGTH_SHORT).show();
                }else{
                    Persona persona = new  Persona();
                    persona.setUid(UUID.randomUUID().toString());
                    persona.setNombre(txt_nombre.getText().toString());
                    persona.setApellidos(txt_apellidos.getText().toString());
                    persona.setCorreo(txt_correo.getText().toString());
                    persona.setPassword(txt_password.getText().toString());

                    databaseReference.child("Persona").child(persona.getUid()).setValue(persona);
                    Toast.makeText(getApplicationContext(), "Agregado",Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                }
            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = new Persona();
                persona.setUid(personaSelected.getUid());
                databaseReference.child("Persona").child(persona.getUid()).removeValue();
                Toast.makeText(getApplicationContext(), "Eliminado",Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = new Persona();
                persona.setUid(personaSelected.getUid());

                persona.setNombre(txt_nombre.getText().toString());
                persona.setApellidos(txt_apellidos.getText().toString());
                persona.setCorreo(txt_correo.getText().toString());
                persona.setPassword(txt_password.getText().toString());
                databaseReference.child("Persona").child(persona.getUid()).setValue(persona);
                Toast.makeText(getApplicationContext(), "Guardado",Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        });

    }

    private void limpiarCampos(){
        txt_nombre.setText("");
        txt_correo.setText("");
        txt_password.setText("");
        txt_apellidos.setText("");
    }

    private void validacion(){

        String nombre = txt_nombre.getText().toString();
        String correo = txt_correo.getText().toString();
        String password = txt_password.getText().toString();

        if(nombre.equals("")){
            txt_nombre.setError("Required");
        }else if(correo.equals("")){
            txt_correo.setError("Required");
        }else if(password.equals("")) {
            txt_password.setError("required");
        }
    }
}