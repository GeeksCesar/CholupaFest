package smartgeeks.cholupafest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import smartgeeks.cholupafest.Object.Noticia;
import smartgeeks.cholupafest.R;

/**
 * Created by CesarLiizcano on 26/09/2017.
 */

public class noticiasAdapter extends RecyclerView.Adapter<noticiasAdapter.viewHolder>{

    Context context;
    List<Noticia> noticiaList ;

    public noticiasAdapter(Context context, List<Noticia> noticiaList) {
        this.context = context;
        this.noticiaList = noticiaList;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_noticia, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        Noticia noticia = noticiaList.get(position);

        holder.tvTitle.setText(noticia.getTitleNotice());
        holder.tvDescripcion.setText(noticia.getDescripcionNotice());

        Glide.with(context).load(noticia.getUrlImgNotice()).into(holder.imgNotice);
    }

    @Override
    public int getItemCount() {
        return noticiaList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView imgNotice ;
        TextView tvTitle, tvDescripcion;

        public viewHolder(View itemView) {
            super(itemView);

            imgNotice = (ImageView) itemView.findViewById(R.id.ivNoticia);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitleNotice);
            tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcionNotice);

        }
    }

}
