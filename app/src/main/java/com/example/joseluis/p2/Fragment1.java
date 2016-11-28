package com.example.joseluis.p2;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by joseluis on 6/11/16.
 */

public class Fragment1 extends Fragment {

    DataBaseManager manager;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private TextView cara_id,cara_nombre;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view= inflater.inflate(R.layout.fragment1,container);
        manager= new DataBaseManager(view.getContext());
        lista= (ListView) view.findViewById(R.id.listView);
        String[] from = new String[] {manager.COLUMNA_ID,manager.COLUMNA_CARACTERISTICA};
        int[] to = new int[] {R.id.caracteristica_id,R.id.caracteristica};

        cursor = manager.cargarCursor();
        adapter= new SimpleCursorAdapter(view.getContext(), R.layout.formato_elemento,cursor,from,to,0);
        lista.setAdapter(adapter);

        // acción cuando hacemos click en item para poder modificarlo o eliminarlo
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                cara_id = (TextView) view.findViewById(R.id.caracteristica_id);
                cara_nombre = (TextView) view.findViewById(R.id.caracteristica);

                String aux_miembroId = cara_id.getText().toString();
                String aux_miembroNombre = cara_nombre.getText().toString();

                Intent modify_intent = new Intent(view.getContext().getApplicationContext(), ModificarMiembro.class);
                modify_intent.putExtra("caracId", aux_miembroId);
                modify_intent.putExtra("caracNombre", aux_miembroNombre);
                startActivity(modify_intent);
            }
        });

        return view;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
