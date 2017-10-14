package smartgeeks.cholupafest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import smartgeeks.cholupafest.Conexion.CheckConection;
import smartgeeks.cholupafest.Conexion.WebService;

public class Login extends AppCompatActivity {

    CheckConection conection = new CheckConection();

    Button btnContinuar, btnSinRegistro;
    EditText edCodigoText;


    int code;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String codigo;

    Typeface font;

    //VOLLEY
    RequestQueue mRequestQueue;
    StringRequest mStringRequest;
    String Url;

    int ValidarCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        font = Typeface.createFromAsset(getAssets(), "blowbrush.ttf");

        btnContinuar = (Button) findViewById(R.id.btnContinuar);
        btnSinRegistro = (Button) findViewById(R.id.btnSinRegistro);
        edCodigoText = (EditText) findViewById(R.id.EdCodigo);


        btnContinuar.setTypeface(font);
        btnSinRegistro.setTypeface(font);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (conection.verificaConexion(Login.this)) {
                    codigo = edCodigoText.getText().toString();
                    consultarCodigo(codigo, view);
                } else {
                    conection.showSnackAlert(view, getResources().getString(R.string.alert_no_conexion));
                }

            }
        });


        btnSinRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences(getString(R.string.Datos_Usuario), Context.MODE_PRIVATE);
                editor = preferences.edit();

                editor.putString(getString(R.string.state_usuario), "SessionSucces");
                editor.commit();

                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }
        });

    }

    public void consultarCodigo(final String codigo, final View view) {
        mRequestQueue = Volley.newRequestQueue(this);

        Url = WebService.CONSULTAR_CODIGO + codigo;

        mStringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")) {
                    ValidarCodigo = 1;

                } else {
                    ValidarCodigo = 2;
                }
                if (TextUtils.isEmpty(codigo)) {
                    code = 2;
                } else {
                    code = Integer.parseInt(codigo);
                }

                if (code == 2) {
                    conection.showSnackAlert(view, getResources().getString(R.string.alert_codigo_empty));
                } else if (code >= 10000) {
                    conection.showSnackAlert(view, getResources().getString(R.string.alert_codigo_no_valido));
                } else if (ValidarCodigo == 2) {
                    Intent intent = new Intent(Login.this, Registro.class);
                    intent.putExtra(Registro.CODIGO, codigo);
                    startActivity(intent);
                    finish();
                } else {
                    conection.showSnackAlert(view, "Este codigo ya esta en uso.");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        mRequestQueue.add(mStringRequest);

    }
}
