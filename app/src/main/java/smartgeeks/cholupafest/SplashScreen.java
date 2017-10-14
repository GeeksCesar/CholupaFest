package smartgeeks.cholupafest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.security.MessageDigest;

public class SplashScreen extends AppCompatActivity {

    Thread splash;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        preferences = getSharedPreferences(getString(R.string.Datos_Usuario), Context.MODE_PRIVATE);
        state = preferences.getString(getString(R.string.state_usuario), "SessionFailed");

        if (state.equals("SessionSucces")){
            editor = preferences.edit();
            editor.putString(getString(R.string.state_usuario), "SessionFailed" );
            editor.commit();
        }

         splash =  new Thread(){
            @Override
            public void run() {
                try {
                    //Duracion
                    sleep(3000);
                    //intent al MainActivity
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    //removiendo activity
                    finish();
                }catch (Exception e){

                }
            }
        };
        splash.start();

    }

    public String keyHasher() {
        PackageInfo info;
        String keyHasher = null;
        try {
            info = getPackageManager().getPackageInfo("smartgeeks.cholupafest", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHasher = new String(Base64.encode(md.digest(), 0));
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyHasher;
    }
}
