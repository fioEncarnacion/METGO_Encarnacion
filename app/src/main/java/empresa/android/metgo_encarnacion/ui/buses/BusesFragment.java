package empresa.android.metgo_encarnacion.ui.buses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
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

import empresa.android.metgo_encarnacion.BusAdaptador;
import empresa.android.metgo_encarnacion.Bus;
import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentBusesBinding;

public class BusesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BusAdaptador busAdaptador;
    private List<Bus> busList;
    private static final String URL_OBTENER_BUSES = "http://192.168.1.2:8012/crud_metgo/obtener_buses.php"; // Cambia localhost a tu
                                                                                        // dirección IP si pruebas en un dispositivo físico

    private FragmentBusesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BusesViewModel busesViewModel =
                new ViewModelProvider(this).get(BusesViewModel.class);


        View vista=inflater.inflate(R.layout.fragment_buses,container,false);

        // Inicializar RecyclerView
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear lista de buses con íconos
        busList = new ArrayList<>();

      // Cargar los datos desde la base de datos
        cargarBusesDesdeBaseDeDatos();

        // Configurar el adaptador
        busAdaptador = new BusAdaptador(busList);
        recyclerView.setAdapter(busAdaptador);




        return vista;


    }

    private void cargarBusesDesdeBaseDeDatos() {
        // Crear una nueva solicitud JSON
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_OBTENER_BUSES, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject busObject = response.getJSONObject(i);
                                String codigo = busObject.getString("codigo");
                                String nombre = busObject.getString("nombre");
                                int icono = R.drawable.icono_bus_postivivo; // Usa un ícono predeterminado

                                // Agregar el bus a la lista
                                busList.add(new Bus(codigo, nombre, icono));
                            }
                            // Notificar al adaptador que los datos han cambiado
                            busAdaptador.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

        // Añadir la solicitud a la cola de peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }





 @Override
 public void onResume() {
     super.onResume();
     // Establece el título del fragmento de búsqueda cuando se reanuda
     getActivity().setTitle("Buses");
 }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


/*   busList.add(new Bus("AN-04", "B. ALIMENTADOR COLLIQUE", R.drawable.icono_bus_postivivo));
        busList.add(new Bus("AN-09", "B. ALIMENTADOR CARABAYLLO", R.drawable.icono_bus_postivivo));
        busList.add(new Bus("AN-08", "B. ALIMENTADOR MILAGROS DE JESUS", R.drawable.icono_bus_postivivo));
        busList.add(new Bus("AN-07", "B. ALIMENTADOR BELAUNDE", R.drawable.icono_bus_postivivo));
        busList.add(new Bus("AN-06", "B. ALIMENTADOR PUNO", R.drawable.icono_bus_postivivo));
*/