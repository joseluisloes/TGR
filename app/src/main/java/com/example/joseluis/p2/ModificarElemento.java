package com.example.joseluis.p2;

/**
 * Created by joseluis on 4/11/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModificarElemento extends Activity implements OnClickListener {

    EditText et;
    Button btnActualizar, btnEliminar;

    long member_id;
    String nombreTabla, nombreElemento;

    DataBaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_elemento);

        db = new DataBaseManager(this);
        et = (EditText) findViewById(R.id.et_elemento_id);
        btnActualizar = (Button) findViewById(R.id.btnActualizarElemento);
        btnEliminar = (Button) findViewById(R.id.btnEliminarElemento);

        Intent i = getIntent();
        String memberID = i.getStringExtra("mId");
        String memberName = i.getStringExtra("mNombre");
        String tabla = i.getStringExtra("NombreTabla");

        member_id = Long.parseLong(memberID);
        nombreElemento = memberName;
        nombreTabla = tabla;

        et.setText(nombreElemento);

        btnActualizar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActualizarElemento:
                String memName_upd = et.getText().toString();
                db.modificarelemento(nombreTabla, member_id, memName_upd);
                Intent main = new Intent(ModificarElemento.this, MyActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;

            case R.id.btnEliminarElemento:
                db.eliminarelemento(nombreTabla, member_id);
                Intent main2 = new Intent(ModificarElemento.this, MyActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main2);
                break;
        }
    }
}