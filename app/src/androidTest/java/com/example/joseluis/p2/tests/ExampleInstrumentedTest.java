package com.example.joseluis.p2.tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.joseluis.p2.DataBaseManager;

import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// ./gradlew connectedAndroidTest

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void prueba_insertar() {

        DataBaseManager db;
        Context context;
        context = InstrumentationRegistry.getTargetContext();
        db = new DataBaseManager(context);
        int contador = db.getCount();
        db.insertar("perro", context);
        db.insertar("gato", context);
        db.insertar("vaca", context);
        //comprobar que se insertaron 3 datos
        assertEquals(db.getCount(), contador + 3);
        //comprobar que los datos insertados son correctos
        assertTrue(db.existe("perro"));
        assertTrue(db.existe("gato"));
        assertTrue(db.existe("vaca"));
        
        assertFalse(db.existe("sdfaa"));
        //comprobar que se crearon las diferentes tablas
        assertTrue(db.existe_tabla("perro"));
        assertTrue(db.existe_tabla("gato"));
        assertTrue(db.existe_tabla("vaca"));
        assertFalse(db.existe_tabla("sdfaa"));

    }
}
