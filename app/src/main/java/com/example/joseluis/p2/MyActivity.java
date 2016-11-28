package com.example.joseluis.p2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MyActivity extends ActionBarActivity implements OnClickListener {


    private Button btnAgregarCaracteristica;
    ViewGroup containerA;
    ViewGroup containerB;    EditText et;
    Button btnAgregar, read_bt;
    DataBaseManager db;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    Fragment1 fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        containerB = (ViewGroup) this.findViewById(R.id.container_b);
        containerA = (ViewGroup) this.findViewById(R.id.container_a);

        if (containerB ==(null)){
            btnAgregarCaracteristica = (Button) findViewById(R.id.btnAgregarCaracteristica);

            //acción del boton agregar miembro
            btnAgregarCaracteristica.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iagregar = new Intent(MyActivity.this, AgregarMiembro.class);
                    startActivity(iagregar);
                };
            });
        }else {

            et = (EditText) findViewById(R.id.et_carac_id);
            db= new DataBaseManager(this);
            btnAgregar = (Button) findViewById(R.id.btnAgregar);

            btnAgregar.setOnClickListener(this);


            };
    }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnAgregar:
                    String name = et.getText().toString();
                    db.insertar(name,this);
                    Intent main = new Intent(MyActivity.this, MyActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                    break;
                default:
                    break;
            }
    }


}


       /*
//Paso 1: Obtener la instancia del administrador de fragmentos
        FragmentManager fragmentManager = getFragmentManager();
        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //Paso 3: Crear un nuevo fragmento y añadirlo
        //CountFragment fragment = new CountFragment();
        //transaction.add(R.id.contenedor, fragment);
        //Paso 4: Confirmar el cambio
        transaction.commit();*/

