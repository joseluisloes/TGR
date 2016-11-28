package com.example.joseluis.p2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by joseluis on 3/11/16.
 */

public class DataBaseManager {

    public static final String TABLE_NAME ="caracteristicas";

    public static final String COLUMNA_ID ="_id";
    public static final String COLUMNA_CARACTERISTICA="caracteristica";

    public static final String COL_ID ="_id";
    public static final String COL_ELEMENTO="caracteristica";

    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "(" + COLUMNA_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMNA_CARACTERISTICA + " TEXT NOT NULL);";

    private BdHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new BdHelper(context);
        db = helper.getWritableDatabase();
    }



    public int insertar(String caract,Context context){
    //Al insertar una categoría creamos una nueva base de datos

        ContentValues valores= new ContentValues();
        //valores.put(nomcolumna, valor)
        valores.put(COLUMNA_CARACTERISTICA,caract);
        //db.insert(tabla,nomcolumnaHACK,CONTENEDOR DE VALORES)
        try {
            db.execSQL("create table "
                    + caract + "(" + COL_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ELEMENTO + " TEXT NOT NULL);");
            db.insert(TABLE_NAME,null,valores);
            return 1;
        }
        catch (SQLException e)
        {
            Toast.makeText(context, " Valor  incorrecto  ", Toast.LENGTH_SHORT).show();
            return 0;
        }

    }

    public void insertarElemento(String tabla, String elemento,Context context){
        ContentValues valores= new ContentValues();
        //valores.put(nomcolumna, valor)
        valores.put(COL_ELEMENTO,elemento);
        //db.insert(tabla,nomcolumnaHACK,CONTENEDOR DE VALORES)
        try {
            db.insert(tabla, null, valores);
        }
        catch (SQLException e)
        {
            Toast.makeText(context, " Valor  incorrecto  ", Toast.LENGTH_SHORT).show();

        }
    }

    public void eliminar(long id,String nombre,Context context) {
        //db.delete(Tabla, clausula where, argumentos where)
        try {
            db.delete(TABLE_NAME, COLUMNA_ID + "=" + id, null);
            db.execSQL("drop table "+ nombre );
        }
        catch (SQLException e)
        {
            Toast.makeText(context, " Imposible eliminar  ", Toast.LENGTH_SHORT).show();

        }

    }

    public void eliminarelemento(String table, long id) {
        db.delete(table, COL_ID +"=" + id, null);
    }


    public void modificar(long id,String nombre, String nuevacarac,Context cnt) {
        ContentValues valores = new ContentValues();
        //valores.put(nomcolumna, valor)
        valores.put(COLUMNA_CARACTERISTICA, nuevacarac);

        db.update(TABLE_NAME, valores, COLUMNA_ID + " = " + id, null);
        if (!nombre.equals(nuevacarac)) {
            db.execSQL("alter table "+ nombre +" rename to "+ nuevacarac );
        }
    }

    public void modificarelemento(String tabla, long id, String nuevoelemento ) {
        ContentValues valores = new ContentValues();
        valores.put(COL_ELEMENTO, nuevoelemento);
        db.update(tabla, valores, COL_ID + " = " +id,null);

    }


    public Cursor cargarCursor() {
        String[] columnas = new String[] {COLUMNA_ID, COLUMNA_CARACTERISTICA};
        return db.query(TABLE_NAME, columnas, null, null, null, null, null);
    }

    public Cursor CargarCursorElementos(String tabla) {
        String[] columnas = new String[] {COL_ID,COL_ELEMENTO};

        return db.query(tabla, columnas, null,null,null,null,null);
    }


    public Cursor CargarAleatorio(String Tabla){
        Cursor cursor = db.rawQuery("SELECT * FROM "+Tabla +" ORDER BY RANDOM() LIMIT 1;",null);
        return cursor;
    }
//Para los tests
    public int getCount(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        //cursor.close();

        return cursor.getCount();
    }

    public boolean existe(String nombre) {

        Cursor cursor = null;
        String[] columnas = new String[]{COLUMNA_ID, COLUMNA_CARACTERISTICA};

        try{
            cursor = db.query(TABLE_NAME, columnas, COLUMNA_CARACTERISTICA + "=?", new String[]{nombre}, null, null, null);

            if (cursor.getCount() == 0) return false;
            else return true;
        }catch (SQLException e) {
            return false;
        }
    }


    public boolean existe_tabla(String nombre){

        Cursor cursor=null;
        String[] columnas = new String[]{COL_ID, COL_ELEMENTO};

        try {
            cursor = db.query(nombre, columnas, null, null, null, null, null);
            if (cursor == null) return false;
            else return true;
        }catch (SQLException e) {
           return false;
        }
    }


}
