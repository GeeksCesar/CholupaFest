package smartgeeks.cholupafest.Menu;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import smartgeeks.cholupafest.Conexion.CheckConection;
import smartgeeks.cholupafest.Conexion.WebService;
import smartgeeks.cholupafest.Object.Noticia;
import smartgeeks.cholupafest.R;
import smartgeeks.cholupafest.adapter.RecyclerItemClickListener;
import smartgeeks.cholupafest.adapter.noticiasAdapter;


public class Noticias extends Fragment {

    View view;
    CheckConection conection ;

    //VOLLEY
    JsonArrayRequest jsonArrayRequest;
    RequestQueue mRequestQueue;

    int idNoticia;
    String titleNoticia, descripcionNoticia, imgNoticia;

    TextView tvTitleNoticias;
    RecyclerView rvNoticia;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout refreshLayout;

    //adapter
    private  noticiasAdapter adapter;
    List<Noticia> noticiaList ;

    Typeface font ;

    public Noticias() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.menu_noticias, container, false);

        tvTitleNoticias = (TextView) view.findViewById(R.id.tvTitleNoticia);
        rvNoticia = (RecyclerView) view.findViewById(R.id.rvNoticias);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        //FontType
        font = Typeface.createFromAsset(getActivity().getAssets(), "blowbrush.ttf");

        tvTitleNoticias.setTypeface(font);

        noticiaList = new ArrayList<>();
        adapter = new noticiasAdapter(getActivity(), noticiaList);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rvNoticia.setLayoutManager(layoutManager);
        rvNoticia.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataListNoticia();
                        refreshLayout.setRefreshing(false);
                        refreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
                    }
                }, 2000);

            }
        });

        dataListNoticia();

        //CLick Recyvlerview
        rvNoticia.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                idNoticia = noticiaList.get(position).getIdNotice();
                titleNoticia = noticiaList.get(position).getTitleNotice();
                descripcionNoticia = noticiaList.get(position).getDescripcionNotice();
                imgNoticia = noticiaList.get(position).getUrlImgNotice();

                DetalleNoticia detalleNoticia = new DetalleNoticia();
                Bundle args = new Bundle();

                args.putInt(DetalleNoticia.idNoticia, idNoticia);
                args.putString(DetalleNoticia.titleNoticia, titleNoticia);
                args.putString(DetalleNoticia.descripcionNoticia, descripcionNoticia);
                args.putString(DetalleNoticia.imageNoticia, imgNoticia);

                detalleNoticia.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.content, detalleNoticia, "DETALLE").commit();

            }
        }));

        return view;
    }

    private void dataListNoticia(){

        noticiaList.clear();

        mRequestQueue = Volley.newRequestQueue(getActivity());

        jsonArrayRequest = new JsonArrayRequest(WebService.CONSULTAR_NOTICIAS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                dataNoticias(jsonArray);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(jsonArrayRequest);
    }

    private void dataNoticias(JSONArray array) {

        Log.d(WebService.TAG, "json noticia :"+ array);
        if (array.length() > 0){

            for (int i=0; i < array.length(); i++){
                Noticia noticia = new Noticia();

                JSONObject json = null;

                try {
                    json = array.getJSONObject(i);

                    noticia.setIdNotice(json.getInt(WebService.ID));
                    noticia.setTitleNotice(json.getString(WebService.NOMBRE));
                    noticia.setDescripcionNotice(json.getString(WebService.DESCRIPCION));
                    noticia.setUrlImgNotice(json.getString(WebService.IMG));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                noticiaList.add(noticia);

            }

            adapter = new noticiasAdapter(getContext(), noticiaList);
            rvNoticia.setAdapter(adapter);

        }else {
            Log.d(WebService.TAG, "no hay data");
        }
    }

}
