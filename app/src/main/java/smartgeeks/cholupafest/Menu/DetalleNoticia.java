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


public class DetalleNoticia extends Fragment {

    View view;

    TextView tvTitleNoticia, tvDescripcion;
    ImageView ivImgNoticia;
    Button btnVolver, btnCompartir ;

    public static String idNoticia= "idNoticia";
    public static String titleNoticia = "titleNotice";
    public static String imageNoticia = "imgNotice";
    public static String descripcionNoticia= "descripcionNotice";

    int getidNotice;
    String getTitleNoticia, getDescripcionNoticia, getImgageNoticia ;

    Typeface font ;

    //compartir Facebook
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    public DetalleNoticia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.menu_detalle_noticia, container, false);

        //Instance Facebook
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        tvTitleNoticia = (TextView) view.findViewById(R.id.tvTitleANoticia);
        tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcionNoticia);
        ivImgNoticia = (ImageView) view.findViewById(R.id.ivImgNoticia);
        btnVolver = (Button) view.findViewById(R.id.btnVolver);
        btnCompartir = (Button) view.findViewById(R.id.btnCompartir);


        //FontType
        font = Typeface.createFromAsset(getActivity().getAssets(), "blowbrush.ttf");

        //Get Arguments
        Bundle bundle = this.getArguments();

        if (bundle != null){

            getidNotice = bundle.getInt(idNoticia, 0);
            getTitleNoticia = bundle.getString(titleNoticia, "");
            getDescripcionNoticia = bundle.getString(descripcionNoticia, "");
            getImgageNoticia= bundle.getString(imageNoticia , "");


        }else {
            Log.d(WebService.TAG, "no hay datos");
        }


        tvTitleNoticia.setText(getTitleNoticia);
        tvDescripcion.setText(getDescripcionNoticia);

        Glide.with(getActivity()).load(getImgageNoticia).asBitmap().into(ivImgNoticia);

        //SetType
        tvTitleNoticia.setTypeface(font);
        btnVolver.setTypeface(font);
        btnCompartir.setTypeface(font);

        //Click Button
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                fragment = new Noticias();

                getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            }
        });


        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=smartgeeks.cholupafest&hl=es"))
                        .setQuote(getDescripcionNoticia)
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
