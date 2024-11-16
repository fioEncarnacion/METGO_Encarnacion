package empresa.android.metgo_encarnacion;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ParadaAdapter extends RecyclerView.Adapter<ParadaAdapter.ParadaViewHolder> {

    private List<Parada> paradaList;

    public ParadaAdapter(List<Parada> paradaList) {
        this.paradaList = paradaList;
    }

    @NonNull
    @Override
    public ParadaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_paradas, parent, false);
        return new ParadaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParadaViewHolder holder, int position) {
        Parada parada = paradaList.get(position);
        holder.paradaCodeTextView.setText(parada.getCodigo());
        holder.paradaNameTextView.setText(parada.getNombre());
        holder.paradaIconImageView.setImageResource(parada.getIconoParada());

        // Limpiar contenedor de íconos de buses antes de agregar nuevos
        holder.paradaBusIconsContainer.removeAllViews();

        // Agregar íconos de buses al contenedor
        for (int iconoBus : parada.getIconosBuses()) {
            ImageView busIcon = new ImageView(holder.itemView.getContext());
            busIcon.setLayoutParams(new LinearLayout.LayoutParams(40, 40));  // Tamaño de cada ícono
            busIcon.setImageResource(iconoBus);
            holder.paradaBusIconsContainer.addView(busIcon);
        }
    }

    @Override
    public int getItemCount() {
        return paradaList.size();
    }

    public static class ParadaViewHolder extends RecyclerView.ViewHolder {

        ImageView paradaIconImageView;
        TextView paradaCodeTextView;
        TextView paradaNameTextView;
        LinearLayout paradaBusIconsContainer;

        public ParadaViewHolder(@NonNull View itemView) {
            super(itemView);
            paradaIconImageView = itemView.findViewById(R.id.paradaIconImageView);
            paradaCodeTextView = itemView.findViewById(R.id.paradaCodeTextView);
            paradaNameTextView = itemView.findViewById(R.id.paradaNameTextView);
            paradaBusIconsContainer = itemView.findViewById(R.id.paradaBusIconsContainer);
        }
    }
}