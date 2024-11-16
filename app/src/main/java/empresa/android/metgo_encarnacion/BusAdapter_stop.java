package empresa.android.metgo_encarnacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusAdapter_stop extends RecyclerView.Adapter<BusAdapter_stop.BusViewHolder>{

    private ArrayList<Bus_stop> busList;

    public BusAdapter_stop(ArrayList<Bus_stop>busList) {
        this.busList = busList;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item_stop, parent, false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        Bus_stop bus_stop = busList.get(position);
        holder.busCodeTextView.setText(bus_stop.getBusCode());
        holder.busNameTextView.setText(bus_stop.getBusName());
        holder.timeArrivalTextView.setText(bus_stop.getFormattedArrivalTime() +" min");
        holder.timeEstimatedTextView.setText(bus_stop.getFormattedEstimatedTime() +" min");
       /* holder.timeArrivalTextView.setText(bus_stop.getTimeArrival() +" min");
        holder.timeEstimatedTextView.setText(bus_stop.getTimeEstimated() +" min");*/
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {
        public TextView busCodeTextView;
        public TextView busNameTextView;
        public TextView timeArrivalTextView;
        public TextView timeEstimatedTextView;
        public ImageView busIconImageView;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            busCodeTextView = itemView.findViewById(R.id.busCodeTextView);
            busNameTextView = itemView.findViewById(R.id.busNameTextView);
            timeArrivalTextView = itemView.findViewById(R.id.timeArrivalTextView);
            timeEstimatedTextView = itemView.findViewById(R.id.timeEstimatedTextView);
            busIconImageView = itemView.findViewById(R.id.busIconImageView);// Añadir icono si es necesario
        }
    }

}
