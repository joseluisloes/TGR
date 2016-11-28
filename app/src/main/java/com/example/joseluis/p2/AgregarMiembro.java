package com.example.joseluis.p2;

/**
 * Created by joseluis on 3/11/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AgregarMiembro extends Activity implements OnClickListener {
    EditText et;
    Button btnAgregar, read_bt;
    DataBaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_agregar);
        et = (EditText) findViewById(R.id.et_carac_id);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);

        db = new DataBaseManager(this);
        btnAgregar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAgregar:
                String name = et.getText().toString();
                db.insertar(name,this);
                Intent main = new Intent(AgregarMiembro.this, MyActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;

            default:
                break;
        }
    }
}