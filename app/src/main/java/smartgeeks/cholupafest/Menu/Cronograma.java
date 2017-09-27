package smartgeeks.cholupafest.Menu;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import smartgeeks.cholupafest.Conexion.CheckConection;
import smartgeeks.cholupafest.Conexion.WebService;
import smartgeeks.cholupafest.Object.Actividad;
import smartgeeks.cholupafest.R;
import smartgeeks.cholupafest.adapter.RecyclerItemClickListener;
import smartgeeks.cholupafest.adapter.actividadesAdapter;

public class Cronograma extends Fragment {

    View view;
    String[] arrayStringDias = {"Viernes", "Sabado", "Domingo" };
    Spinner spinnerDays ;

    CheckConection conection ;

    RecyclerView recyclerViewFecha;
    RecyclerView.LayoutManager layoutManager;
    String daySelected ;


    //adapter
    private actividadesAdapter adapter;
    List<Actividad> actividadList ;

    //VOLLEY
    JsonArrayRequest jsonArrayRequest;
    RequestQueue mRequestQueue;

    String titleActividad, descripcionActividad, imgActividad ;
    int idActividad ;

    TextView tvTitleCronograma;
    Typeface font ;

    public Cronograma() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.menu_cronograma, container, false);

        //PERSONALIZAR SPINNER
        spinnerDays = (Spinner) view.findViewById(R.id.spFecha);
        spinnerDays.setAdapter(new AdapterCategorias(getActivity(), R.layout.row_spinner, arrayStringDias));

        //Intance
        tvTitleCronograma = (TextView) view.findViewById(R.id.tvTitleCronograma);
        recyclerViewFecha = (RecyclerView) view.findViewById(R.id.recyclerviewFecha);

        //FontType
        font = Typeface.createFromAsset(getActivity().getAssets(), "blowbrush.ttf");

        tvTitleCronograma.setTypeface(font);

        actividadList = new ArrayList<>();

        adapter = new actividadesAdapter(getActivity(), actividadList);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerViewFecha.setLayoutManager(layoutManager);
        recyclerViewFecha.setAdapter(adapter);

        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daySelected = String.valueOf(parent.getItemAtPosition(position));
                Log.d("TAG", "day Selected: "+daySelected);

                if (daySelected.equals("Viernes")){
                    dataListCronograma(WebService.URL_DAY_VIERNES);
                }else if (daySelected.equals("Sabado")){
                    dataListCronograma(WebService.URL_DAY_SABADO);
                }else if (daySelected.equals("Domingo")){
                    dataListCronograma(WebService.URL_DAY_DOMINGO);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Click Recyclerview
        recyclerViewFecha.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                idActividad = actividadList.get(position).getIdCronograma();
                titleActividad = actividadList.get(position).getTitle();
                descripcionActividad = actividadList.get(position).getDescripcion();
                imgActividad = actividadList.get(position).getUrlImgActividad();

                DatosActividad datosActividad = new DatosActividad();
                Bundle args = new Bundle();

                args.putInt(DatosActividad.idActividad, idActividad);
                args.putString(DatosActividad.titleActividad, titleActividad);
                args.putString(DatosActividad.descripcionActividad, descripcionActividad);
                args.putString(DatosActividad.imgActividad, imgActividad);

                datosActividad.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.content, datosActividad, "DATOS").commit();

            }
        }));




        return view;
    }


    private void dataListCronograma(String URL){

        Log.d(WebService.TAG, "URl: "+URL);
        actividadList.clear();

        mRequestQueue = Volley.newRequestQueue(getActivity()) ;

        jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setRecyclerViewFecha(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;

        mRequestQueue.add(jsonArrayRequest) ;

    }

    private void setRecyclerViewFecha(JSONArray array){
        Log.d(WebService.TAG, "json Cronograma :"+ array);
        if (array.length() > 0 ){
            for (int i = 0; i< array.length(); i++){
                    Actividad actividad = new Actividad();

                JSONObject json = null;

                try {
                    json = array.getJSONObject(i);

                    actividad.setIdCronograma(json.getInt(WebService.ID));
                    actividad.setTitle(json.getString(WebService.TITLE));
                    actividad.setDescripcion(json.getString(WebService.DESCRIPCION));
                    actividad.setHora(json.getString(WebService.HORA));
                    actividad.setUrlImgActividad(json.getString(WebService.IMG));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                actividadList.add(actividad);
            }

            adapter = new actividadesAdapter(getActivity(), actividadList);
            recyclerViewFecha.setAdapter(adapter);
        }else {
            Log.d(WebService.TAG, "no hay data");
        }
    }


    //CLASE PERSONALIZAR SPINNER
    public class AdapterCategorias extends ArrayAdapter<String> {


        public AdapterCategorias(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater= getActivity().getLayoutInflater();
            View row=inflater.inflate(R.layout.row_spinner, parent, false);

            TextView label=(TextView)row.findViewById(R.id.textViewCategoria);
            label.setText(arrayStringDias[position]);

            label.setTypeface(font);

            return row;
        }
    }

}
