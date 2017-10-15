package smartgeeks.cholupafest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import smartgeeks.cholupafest.Conexion.CheckConection;
import smartgeeks.cholupafest.Conexion.WebService;

public class Registro extends AppCompatActivity {

    CheckConection conection = new CheckConection();

    public static String CODIGO = "codUsuario";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    EditText edNombre, edTelefono, edEmail;
    Button btnFinalizar;
    RelativeLayout contenedorView;

    String strNombre, strTelefono, strEmail, getCodigoUser, idToken;

    //VOLLEY
    RequestQueue mRequestQueue;
    StringRequest mStringRequest;

    Typeface font;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registro);

        font = Typeface.createFromAsset(getAssets(), "blowbrush.ttf");

        contenedorView = (RelativeLayout) findViewById(R.id.contenedorRegistro);
        edNombre = (EditText) findViewById(R.id.edNombre);
        edTelefono = (EditText) findViewById(R.id.edTelefono);
        edEmail = (EditText) findViewById(R.id.edCorreo);
        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);

        btnFinalizar.setTypeface(font);

        bundle = getIntent().getExtras();

        getCodigoUser = bundle.getString(CODIGO);


        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = edEmail.getText().toString().trim();
                strNombre = edNombre.getText().toString().trim();
                strTelefono = edTelefono.getText().toString().trim();

                getToken();
                Log.d(WebService.TAG, "token: " + idToken);

                if (strNombre.equals("") || strTelefono.equals("")) {

                    conection.showSnackAlert(view, getResources().getString(R.string.alert_edit_empty));

                }else if(strTelefono.length() != 7 && strTelefono.length() != 10){
                    conection.showSnackAlert(view, getResources().getString(R.string.alert_telefono));
                }
                else if(conection.verificaConexion(Registro.this)){
                    setUsuario(getCodigoUser, strNombre, strTelefono, strEmail, idToken);
                }
            }
        });


    }

    public void setUsuario(final String getCodigoUser, final String strNombre, final String strTelefono, final String strEmail, final String idToken) {
        mRequestQueue = Volley.newRequestQueue(this);

        mStringRequest = new StringRequest(Request.Method.POST, WebService.SET_USUARIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(WebService.TAG, "response: " + response);
                setPreferences(getCodigoUser);
                startActivity(new Intent(Registro.this, MainActivity.class));
                finish();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof NetworkError) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_no_conexion));
                } else if (volleyError instanceof NoConnectionError) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_no_conexion));
                } else if (volleyError instanceof ServerError) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_no_conexion));
                } else if (volleyError instanceof TimeoutError) {
                    conection.showSnackAlert(contenedorView, getResources().getString(R.string.alert_no_conexion));
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("codigo", getCodigoUser);
                params.put("nombre", strNombre);
                params.put("telefono", strTelefono);
                params.put("correo", strEmail);
                params.put("token", idToken);

                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    private void setPreferences(String getCodigoUser) {
        preferences = getSharedPreferences(getString(R.string.Datos_Usuario), Context.MODE_PRIVATE);
        editor = preferences.edit();

        editor.putString(getString(R.string.state_usuario), "SessionUserReg");
        editor.putString(getString(R.string.codigo_usuario), getCodigoUser);
        editor.commit();
    }

    private void getToken() {
        idToken = FirebaseInstanceId.getInstance().getToken();
    }
}
