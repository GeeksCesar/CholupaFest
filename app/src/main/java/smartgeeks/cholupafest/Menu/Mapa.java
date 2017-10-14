package smartgeeks.cholupafest.Menu;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import smartgeeks.cholupafest.Conexion.WebService;
import smartgeeks.cholupafest.R;


public class Mapa extends Fragment {

    View view;
    TextView tvTitleMapa;
    Typeface font ;
    WebView myWebView;

    public Mapa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.menu_mapa, container, false);

        tvTitleMapa = (TextView) view.findViewById(R.id.tvTitleMapa);
        myWebView  = (WebView) view.findViewById(R.id.webView) ;
        //FontType
        font = Typeface.createFromAsset(getActivity().getAssets(), "blowbrush.ttf");
        tvTitleMapa.setTypeface(font);

        myWebView.loadUrl(WebService.CONSULTAR_MAPA);
        myWebView.getSettings().setJavaScriptEnabled(true);


        return view;
    }

}
