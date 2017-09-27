package smartgeeks.cholupafest.Menu;


import android.content.Intent;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import smartgeeks.cholupafest.Conexion.WebService;
import smartgeeks.cholupafest.R;


public class DatosActividad extends Fragment {

    View view;

    ImageView ivImgActividad;
    TextView tvTitleActividad , tvDescripcionActividad;
    Button btnVolver, btnAsistir ;

    public static String idActividad = "idActividad";
    public static String titleActividad = "titleActividad";
    public static String imgActividad = "imgActividad";
    public static String descripcionActividad = "descripcionActividad";

    int getidActividad;
    String getTitleActividad, getImgActividad, getDescripcionActividad;

    Typeface font ;

    //compartir Facebook
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    public DatosActividad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.menu_datos_actividad, container, false);

        //Instance Facebook
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        tvTitleActividad = (TextView) view.findViewById(R.id.tvTitleActividad) ;
        ivImgActividad = (ImageView) view.findViewById(R.id.ivImgActividad);
        tvDescripcionActividad = (TextView) view.findViewById(R.id.tvDescripcionActividad);
        btnVolver = (Button) view.findViewById(R.id.btnVolver) ;
        btnAsistir= (Button) view.findViewById(R.id.btnAsistir) ;

        //FontType
        font = Typeface.createFromAsset(getActivity().getAssets(), "blowbrush.ttf");

        //Get Arguments
        Bundle bundle = this.getArguments();

        if (bundle != null){

            getidActividad = bundle.getInt(idActividad, 0);
            getTitleActividad = bundle.getString(titleActividad, "");
            getDescripcionActividad = bundle.getString(descripcionActividad, "");
            getImgActividad = bundle.getString(imgActividad, "");

        }else {
            Log.d(WebService.TAG, "no hay datos");
        }

        tvTitleActividad.setText(getTitleActividad);
        tvDescripcionActividad.setText(getDescripcionActividad);

        Glide.with(getActivity()).load(getImgActividad).asBitmap().into(ivImgActividad);

        //SETTYPE
        tvTitleActividad.setTypeface(font);
        btnVolver.setTypeface(font);
        btnAsistir.setTypeface(font);

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
                CompartirFacebook();
            }
        });

        return view;
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
