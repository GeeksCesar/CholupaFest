package smartgeeks.cholupafest.Conexion;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by CesarLiizcano on 21/09/2017.
 */

public class CheckConection extends Activity {

    Snackbar snackbar;

    public void showSnackAlert(View view, String textAlert){

        snackbar = Snackbar.make(view, textAlert, Snackbar.LENGTH_SHORT);

        View viewSnackar = snackbar.getView();

        snackbar.setDuration(2000);

        TextView mTextView = (TextView) viewSnackar.findViewById(android.support.design.R.id.snackbar_text);
        viewSnackar.setBackgroundColor(Color.parseColor("#FFBB02"));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        else
            mTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        snackbar.show();
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
