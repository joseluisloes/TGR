package com.example.joseluis.p2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import static com.example.joseluis.p2.R.id.btnAgregarCaracteristica;
import static com.example.joseluis.p2.R.id.btnAnadirElemento;
import static com.example.joseluis.p2.R.id.btnEliminar;


/**
 * Created by joseluis on 3/11/16.
 */
public class ModificarMiembro extends Activity implements OnClickListener {

    private EditText et,ele;
    private Button BtnModificar, BtnBorrar, BtnAnadirElemento;
    private long id;
    private String nombre;
    private DataBaseManager db;
    private TextView columnaID, columnaElemento;
    private ListView lista;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    //Agitacion
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    //Rotacion
    private SensorManager rSensorManager;
    private Sensor rRotacion;
    private GiroDetector rGiroDetector;
    //Giro
    private SensorManager dSensorManager;
    private Sensor dOrientacion;
    private DownDetector dGiroDetector;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_modificar);

        //Agitación

        mSensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector=new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });

        //Rotacion

        rSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        rRotacion = rSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        rGiroDetector = new GiroDetector();
        rGiroDetector.setOnGiroListener(new GiroDetector.OnGiroListener(){
            @Override
            public void onGiro(int count) {
                handleShakeEvent(count);
            }
        });


        //Boca abajo

        dSensorManager= (SensorManager) getSystemService(this.SENSOR_SERVICE);
        dOrientacion = dSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        dGiroDetector = new DownDetector();
        dGiroDetector.setOnGiroListener(new GiroDetector.OnGiroListener() {
            @Override
            public void onGiro(int count) {
                    handleShakeEvent(count);
            }
        });


        db = new DataBaseManager(this);
        et = (EditText) findViewById(R.id.et_miembro_id);
        ele =(EditText) findViewById(R.id.editText2);

        BtnModificar = (Button) findViewById(R.id.btnActualizar);
        BtnBorrar = (Button) findViewById(btnEliminar);
        BtnAnadirElemento = (Button) findViewById(btnAnadirElemento);
        Intent i = getIntent();
        String memberID = i.getStringExtra("caracId");
        String memberName = i.getStringExtra("caracNombre");

        id = Long.parseLong(memberID);
        nombre = memberName;
        et.setText(memberName);

        lista = (ListView) findViewById(R.id.ListViewElemento);
        //acción del boton agregar elemento

        String[] from = new String[] {db.COL_ID,db.COL_ELEMENTO};
        int[] to = new int[] {R.id.caracteristica_id,R.id.caracteristica};
       /* db.insertarElemento(memberName,"asef",this);
        db.insertarElemento(memberName,"ajjjef",this);
        db.insertarElemento(memberName,"asssssssef",this);
        db.insertarElemento(memberName,"assaaaaasef",this);
        db.insertarElemento(memberName,"aaaaaaaaaaa",this);*/



        cursor = db.CargarCursorElementos(memberName);
        adapter= new SimpleCursorAdapter(this, R.layout.formato_elemento,cursor,from,to,0);
        lista.setAdapter(adapter);
        cursor = db.CargarCursorElementos(nombre);
        adapter.changeCursor(cursor);

        // acción cuando hacemos click en item para poder modificarlo o eliminarlo
        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                columnaID = (TextView) view.findViewById(R.id.caracteristica_id);
                columnaElemento = (TextView) view.findViewById(R.id.caracteristica);


                String aux_miembroId = columnaID.getText().toString();
                String aux_miembroNombre = columnaElemento.getText().toString();

                Intent modify_intent2 = new Intent(getApplicationContext(), ModificarElemento.class);
                modify_intent2.putExtra("mId", aux_miembroId);
                modify_intent2.putExtra("mNombre", aux_miembroNombre);
                modify_intent2.putExtra("NombreTabla",nombre);
                startActivity(modify_intent2);
            }
        });

        BtnAnadirElemento.setOnClickListener(this);
        BtnModificar.setOnClickListener(this);
        BtnBorrar.setOnClickListener(this);
    }

    private void handleShakeEvent(int count) {
        cursor = db.CargarAleatorio(nombre);
        adapter.changeCursor(cursor);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActualizar:
                String memName_upd = et.getText().toString();
                db.modificar(id, nombre, memName_upd,this);
                this.returnHome();
                break;

            case R.id.btnEliminar:
                db.eliminar(id,nombre,this);
                this.returnHome();
                break;

            case R.id.btnAnadirElemento:
                String name = ele.getText().toString();
                db.insertarElemento(nombre,name,this);
                cursor = db.CargarCursorElementos(nombre);
                adapter.changeCursor(cursor);
                break;

        }
    }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                MyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }

    //Agitación Rotacion
    @Override
    public void onResume () {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        rSensorManager.registerListener(rGiroDetector,rRotacion,SensorManager.SENSOR_DELAY_GAME);
        dSensorManager.registerListener(dGiroDetector,dOrientacion,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onPause () {
        //mSensorManager.unregisterListener(mShakeDetector);
        rSensorManager.unregisterListener(rGiroDetector);
        dSensorManager.unregisterListener(dGiroDetector);
        super.onPause();
    }

}
