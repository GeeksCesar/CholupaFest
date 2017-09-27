package smartgeeks.cholupafest.Menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smartgeeks.cholupafest.R;


public class DetalleNoticia extends Fragment {

    View view;

    public DetalleNoticia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.menu_detalle_noticia, container, false);





        return view;
    }

}
