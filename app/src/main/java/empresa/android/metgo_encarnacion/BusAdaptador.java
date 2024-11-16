package empresa.android.metgo_encarnacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BusAdaptador extends RecyclerView.Adapter<BusAdaptador.BusViewHolder>{

    private List<Bus> busList;

    public BusAdaptador(List<Bus> busList){
        this.busList = busList;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buses, parent, false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        Bus bus = busList.get(position);
        holder.busCodeTextView.setText(bus.getCode());
        holder.busNameTextView.setText(bus.getName());
        holder.busIconImageView.setImageResource(bus.getIcon()); // Set the icon image
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {
        TextView busCodeTextView, busNameTextView;
        ImageView busIconImageView; // ImageView for the icon

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            busCodeTextView = itemView.findViewById(R.id.busCodeTextView);
            busNameTextView = itemView.findViewById(R.id.busNameTextView);
            busIconImageView = itemView.findViewById(R.id.busIconImageView); // Reference to ImageView
        }
    }
}
