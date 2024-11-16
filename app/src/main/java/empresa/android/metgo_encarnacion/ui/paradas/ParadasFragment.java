package empresa.android.metgo_encarnacion.ui.paradas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import empresa.android.metgo_encarnacion.Parada;
import empresa.android.metgo_encarnacion.ParadaAdapter;
import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentParadasBinding;

public class ParadasFragment extends Fragment {

    private RecyclerView paradaRecyclerView;
    private ParadaAdapter paradaAdapter;
    private List<Parada> paradaList;
    private static final String URL_OBTENER_PARADAS = "http://192.168.1.2:8012/crud_metgo/obtener_paradas.php"; // Cambia a la IP correcta


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ParadasViewModel paradasViewModel =
                new ViewModelProvider(this).get(ParadasViewModel.class);

        View vista = inflater.inflate(R.layout.fragment_paradas, container, false);

        // Configuración del RecyclerView
        paradaRecyclerView = vista.findViewById(R.id.paradaRecyclerView);
        paradaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        // Inicializar la lista de paradas
        paradaList = new ArrayList<>();
        paradaAdapter = new ParadaAdapter(paradaList);
        paradaRecyclerView.setAdapter(paradaAdapter);

        // Cargar las paradas desde la base de datos
        cargarParadasDesdeBaseDeDatos();

        return vista;
    }

    // Método para cargar las paradas desde la base de datos utilizando Volley
    private void cargarParadasDesdeBaseDeDatos() {
        // Crear una nueva solicitud JSON para obtener las paradas
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_OBTENER_PARADAS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject paradaObject = response.getJSONObject(i);
                                String codigo = paradaObject.getString("codigo");
                                String nombre = paradaObject.getString("nombre");

                                // Obtener el ícono de la parada (puede ser un nombre o un recurso de imagen)
                                String iconoStr = paradaObject.getString("icono"); // nombre del ícono
                                int icono = obtenerIconoPorNombre(iconoStr); // Convertirlo a un recurso de imagen

                                // Obtener los íconos de los buses relacionados
                                JSONArray iconosBusesArray = paradaObject.getJSONArray("iconosBuses");
                                int[] iconosBuses = new int[iconosBusesArray.length()];
                                for (int j = 0; j < iconosBusesArray.length(); j++) {
                                    String nombreIconoBus = iconosBusesArray.getString(j);
                                    iconosBuses[j] = obtenerIconoPorNombre(nombreIconoBus);
                                }

                                // Crear un objeto Parada y agregarlo a la lista
                                paradaList.add(new Parada(codigo, nombre, icono, iconosBuses));
                            }
                            // Notificar al adaptador que los datos han cambiado
                            paradaAdapter.notifyDataSetChanged();
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

    // Método para obtener el recurso del ícono correspondiente a partir de su nombre
    private int obtenerIconoPorNombre(String nombreIcono) {
        switch (nombreIcono) {
            case "ic_bus_stop":
                return R.drawable.ic_bus_stop; // Asegúrate de que este ícono esté en tu carpeta drawable
            case "icon_bus_1":
                return R.drawable.icon_bus_1;
            case "icon_bus_2":
                return R.drawable.icon_bus_2;
            case "icon_bus_3":
                return R.drawable.icon_bus_3;
            case "icon_bus_4":
                return R.drawable.icon_bus_4;
            // Agrega más casos según sea necesario
            default:
                return R.drawable.ic_bus_stop; // Ícono predeterminado en caso de que no se encuentre
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        // Establece el título del fragmento de búsqueda cuando se reanuda
        getActivity().setTitle("Paradas");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Object binding = null;
    }


}


/* private List<Parada> crearListaParadas() {
        List<Parada> paradas = new ArrayList<>();
        paradas.add(new Parada("P01", "Parada Central", R.drawable.ic_bus_stop, new int[]{R.drawable.icon_bus_4, R.drawable.icon_bus_3}));
        paradas.add(new Parada("P02", "Parada Sur", R.drawable.ic_bus_stop, new int[]{R.drawable.icon_bus_2}));
        paradas.add(new Parada("P03", "Parada Central", R.drawable.ic_bus_stop, new int[]{R.drawable.icon_bus_4, R.drawable.icon_bus_3}));
        paradas.add(new Parada("P04", "Parada Sur", R.drawable.ic_bus_stop, new int[]{R.drawable.icon_bus_2,R.drawable.icon_bus_1,R.drawable.icon_bus_3}));

        // Agregar más paradas según sea necesario
        return paradas;
    }
*/

    /* Crear una lista de paradas de ejemplo
       paradaList = crearListaParadas();
       paradaAdapter = new ParadaAdapter(paradaList);
       paradaRecyclerView.setAdapter(paradaAdapter);*/