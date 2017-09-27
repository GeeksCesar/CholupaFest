package smartgeeks.cholupafest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import smartgeeks.cholupafest.Menu.Cronograma;
import smartgeeks.cholupafest.Menu.Mapa;
import smartgeeks.cholupafest.Menu.Noticias;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation ;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_noticias:
                    setFragment(0);
                    return true;
                case R.id.navigation_cronograma:
                    setFragment(1);
                    return true;
                case R.id.navigation_mapa:
                    setFragment(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationViewHelper.disableShiftMode(navigation);

        setFragment(0);
    }


    public void setFragment(int pos){
        Fragment fragment = null;
        boolean fragmentSeleccionado = false;

        switch (pos) {
            case 0:
                fragment = new Noticias();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "NOTICIAS").commit();
                break;

            case 1:
                fragment = new Cronograma();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "CRONOGRAMA").commit();
                break;

            case 2:
                fragment = new Mapa();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "MAPA").commit();
                break;
        }
    }

}

