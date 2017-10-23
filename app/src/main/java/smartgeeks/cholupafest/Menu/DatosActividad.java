package smartgeeks.cholupafest.Menu;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import smartgeeks.cholupafest.Conexion.CheckConection;
import smartgeeks.cholupafest.Conexion.WebService;
import smartgeeks.cholupafest.R;


public class DatosActividad extends Fragment {

    View view;
    CheckConection conection = new CheckConection();

    ImageView ivImgActividad;
    TextView tvTitleActividad, tvDescripcionActividad, tvWebActividad;
    Button btnVolver, btnAsistir;
    RelativeLayout contenedorView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String estado, codigo;

    //VOLLEY
    RequestQueue mRequestQueue;
    StringRequest mStringRequest;
    private JSONArray result;
    String URl;
    int countAsistentes;

    public static String idActividad = "idActividad";
    public static String titleActividad = "titleActividad";
    public static String imgActividad = "imgActividad";
    public static String descripcionActividad = "descripcionActividad";
    public static String webActividad = "webActividad";
    public static String maxActividad = "maxActividad";
    public static String horaActividad = "horaActidad";

    int getidActividad, getMaxActidad;
    String getTitleActividad, getImgActividad, getDescripcionActividad, getWebActividad, getHoraActividad;

    Typeface font;

    //compartir Facebook
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    public DatosActividad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menu_datos_actividad, container, false);

        //Instance Facebook
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        contenedorView = (RelativeLayout) view.findViewById(R.id.contenedor);
        tvTitleActividad = (TextView) view.findViewById(R.id.tvTitleActividad);
        ivImgActividad = (ImageView) view.findViewById(R.id.ivImgActividad);
        tvWebActividad = (TextView) view.findViewById(R.id.tvWebActividad);
        tvDescripcionActividad = (TextView) view.findViewById(R.id.tvDescripcionActividad);
        btnVolver = (Button) view.findViewById(R.id.btnVolver);
        btnAsistir = (Button) view.findViewById(R.id.btnAsistir);

        //FontType
        font = Typeface.createFromAsset(getActivity().getAssets(), "blowbrush.ttf");

        //Get Arguments
        Bundle bundle = this.getArguments();

        if (bundle != null) {

            getidActividad = bundle.getInt(idActividad, 0);
            getMaxActidad = bundle.getInt(maxActividad, 0);
            getTitleActividad = bundle.getString(titleActividad, "");
            getDescripcionActividad = bundle.getString(descripcionActividad, "");
            getImgActividad = bundle.getString(imgActividad, "");
            getWebActividad = bundle.getString(webActividad, "");
            getHoraActividad = bundle.getString(horaActividad, "");

        } else {
            Log.d(WebService.TAG, "no hay datos");
        }

        countAsistentes(getidActividad);

        tvTitleActividad.setText(getTitleActividad);
        tvDescripcionActividad.setText(getDescripcionActividad);
        tvWebActividad.setText(getWebActividad);

        Glide.with(getActivity()).load(getImgActividad).asBitmap().into(ivImgActividad);

        //SETTYPE
        tvTitleActividad.setTypeface(font);
        btnVolver.setTypeface(font);
        btnAsistir.setTypeface(font);

        readPreferences();

        if (codigo.isEmpty()) {
            btnAsistir.setEnabled(false);
            btnAsistir.getBackground().setAlpha(75);
        } else {
            btnAsistir.setEnabled(true);
        }

        tvWebActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(getWebActividad);

                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                intent.setPackage("com.facebook.katana");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getWebActividad)));
                }
            }
        });


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                fragment = new Cronograma();

                getFragmentManager().beginTransaction().replace(R.id.content, fragment, "DATOS").commit();

            }
        });

        btnAsistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readPreferences();

                if (conection.verificaConexion(getActivity())) {
                    setAsistencia(getidActividad, codigo);
                } else {
                    conection.showSnackAlert(contenedorView, getString(R.string.alert_no_conexion));
                }

            }
        });

        return view;
    }


    private void readPreferences() {
        preferences = getActivity().getSharedPreferences(getString(R.string.Datos_Usuario), Context.MODE_PRIVATE);

        codigo = preferences.getString(getString(R.string.codigo_usuario), "");
    }

    private void setAsistencia(final int getidActividad, final String codigo) {

        mRequestQueue = Volley.newRequestQueue(getActivity());

        mStringRequest = new StringRequest(Request.Method.POST, WebService.SET_ASISTENCIA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Error")) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_codigo_ya_existe));
                } else {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_actividad_exito));
                    Fragment fragment = null;
                    fragment = new Cronograma();
                    getFragmentManager().beginTransaction().replace(R.id.content, fragment, "ACTIVIDAD").commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_no_conexion));
                } else if (error instanceof TimeoutError) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_no_conexion));
                } else if (error instanceof NetworkError) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_no_conexion));
                } else if (error instanceof NoConnectionError) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_no_conexion));
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("idActividad", String.valueOf(getidActividad));
                params.put("codigo", codigo);

                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    private void countAsistentes(final int getidActividad) {
        mRequestQueue = Volley.newRequestQueue(getActivity());

        URl = WebService.CONSULTAR_ASISTENTES + getidActividad;
        URl = URl.replace(" ", "%");


        mStringRequest = new StringRequest(URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(response);
                    result = jsonObject.getJSONArray("result");
                    Log.d(WebService.TAG, "result: " + result);
                    DataCount(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void DataCount(JSONArray array) {
        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {

                try {
                    JSONObject json = array.getJSONObject(i);

                    countAsistentes = json.getInt("cantidad");

                    Log.d(WebService.TAG, "count asistentes: " + countAsistentes);
                    Log.d(WebService.TAG, "max Asistentes: " + getMaxActidad);

                    if (countAsistentes > getMaxActidad) {
                        Log.d(WebService.TAG, "Entro");
                        btnAsistir.setEnabled(false);
                        btnAsistir.getBackground().setAlpha(75);
                    } else {
                        Log.d(WebService.TAG, "no entro");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            countAsistentes = 0;
            Log.d(WebService.TAG, "no hay Asistentes: " + countAsistentes);
        }
    }


    public void CompartirFacebook() {
        try {
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("CholupaFest")
                        .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=smartgeeks.pbapp1&hl=es"))
                        .setQuote("Asistire a el evento")
                        .build();
                shareDialog.show(linkContent);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
