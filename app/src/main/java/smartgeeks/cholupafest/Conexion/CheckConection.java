package smartgeeks.cholupafest.Conexion;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by CesarLiizcano on 21/09/2017.
 */

public class CheckConection extends Activity {

    public void notifySnackbar(View view, String title) {
        Snackbar.make(view, title, Snackbar.LENGTH_LONG).show();
    }

    public boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    public void volleyError(VolleyError error, Context context){
        if (error instanceof TimeoutError){

        }else if ( error instanceof NetworkError){

        }else if ( error instanceof NoConnectionError){

        }else if ( error instanceof ServerError){

        }
    }

}
