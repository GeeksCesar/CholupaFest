package smartgeeks.cholupafest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import smartgeeks.cholupafest.Object.Actividad;
import smartgeeks.cholupafest.R;

/**
 * Created by CesarLiizcano on 26/09/2017.
 */

public class actividadesAdapter extends RecyclerView.Adapter<actividadesAdapter.viewHolder> {

    Context context;
    List<Actividad> actividadList;


    public actividadesAdapter(Context context, List<Actividad> listActividades) {
        this.context = context;
        this.actividadList = listActividades;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_actividad, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Actividad actividad = actividadList.get(position);

        holder.tvTitle.setText(actividad.getTitle());
        holder.tvHora.setText(actividad.getHora());

    }

    @Override
    public int getItemCount() {
        return actividadList.size() ;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvHora;

        public viewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitleActividad);
            tvHora = (TextView) itemView.findViewById(R.id.tvHoraActividad);
        }
    }
}
