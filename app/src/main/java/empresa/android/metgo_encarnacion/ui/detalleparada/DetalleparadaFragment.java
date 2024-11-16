package empresa.android.metgo_encarnacion.ui.detalleparada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import empresa.android.metgo_encarnacion.BusAdapter_stop;
import empresa.android.metgo_encarnacion.Bus_stop;
import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentDetalleparadaBinding;
import empresa.android.metgo_encarnacion.databinding.FragmentTarifarioBinding;
import empresa.android.metgo_encarnacion.ui.tarifario.TarifarioViewModel;

public class DetalleparadaFragment extends Fragment {

    private static final String ARG_CODIGO_PARADA = "codigoParada";
    private String codigoParada;

    private RecyclerView recyclerView;
    private BusAdapter_stop busAdapter;
    private ArrayList<Bus_stop> busList;
    private TextView stopCodeTextView, stopNameTextView;
    private ImageView stopIconImageView;
    private Handler handler = new Handler();
    private Runnable runnable;
    private Toolbar toolbar;

    private static final String URL_OBTENER_PARADA = "http://192.168.1.2:8012/crud_metgo/obtener_parada.php"; // URL para obtener
    // los datos de la parada

    public static DetalleparadaFragment newInstance(String codigoParada) {
        DetalleparadaFragment fragment = new DetalleparadaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CODIGO_PARADA, codigoParada);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codigoParada = getArguments().getString(ARG_CODIGO_PARADA);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DetalleparadaViewModel detalleparadaViewModel =
                new ViewModelProvider(this).get(DetalleparadaViewModel.class);

        View vista=inflater.inflate(R.layout.fragment_detalleparada,container,false);

        // Configurar el Toolbar
        toolbar = vista.findViewById(R.id.toolbar);

        // Configura el toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // Configura el botón de retroceso
        toolbar.setNavigationOnClickListener(v -> {
            // Regresa al fragmento de búsqueda
            getActivity().getSupportFragmentManager().popBackStack();
        });


        // Inicializar los componentes
        stopCodeTextView = vista.findViewById(R.id.stopCodeTextView);
        stopNameTextView = vista.findViewById(R.id.stopNameTextView);
        stopIconImageView = vista.findViewById(R.id.stopIconImageView);
        recyclerView = vista.findViewById(R.id.busRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stopIconImageView.setImageResource(R.drawable.icono_parada); // Icono por defecto

        // Iniciar la lista de buses y configurar el adaptador
        busList = new ArrayList<>();
        busAdapter = new BusAdapter_stop(busList);
        recyclerView.setAdapter(busAdapter);

        // Actualizar los datos de la parada según el código ingresado
        actualizarParada(codigoParada);

        // Configurar el temporizador para actualizar los tiempos
        runnable = new Runnable() {
            @Override
            public void run() {
                for (Bus_stop bus : busList) {
                    bus.decrementTimes();
                }
                busAdapter.notifyDataSetChanged();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);

        return vista;
    }



    private void actualizarParada(String codigo) {
        // Crear la solicitud para obtener la parada desde la base de datos
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OBTENER_PARADA + "?codigo=" + codigo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            // Extraer la información de la parada
                            String codigoParada = jsonResponse.getString("codigo");
                            String nombreParada = jsonResponse.getString("nombre");
                            String iconoParada = jsonResponse.getString("icono");

                            // Actualizar los TextViews con los datos de la parada
                            stopCodeTextView.setText(codigoParada);
                            stopNameTextView.setText(nombreParada);

                            // Establecer el icono de la parada
                            int iconResource = getResources().getIdentifier(iconoParada, "drawable", getContext().getPackageName());
                            stopIconImageView.setImageResource(iconResource);

                            // Limpiar la lista de buses y agregar los nuevos
                            busList.clear();

                            // Obtener los buses de la parada (suponiendo que la respuesta tiene un array de buses)
                            JSONArray busesArray = jsonResponse.getJSONArray("buses");
                            for (int i = 0; i < busesArray.length(); i++) {
                                JSONObject busObject = busesArray.getJSONObject(i);
                                String codigoBus = busObject.getString("codigo");
                                String nombreBus = busObject.getString("nombre");
                                int tiempoEstimado = busObject.getInt("tiempoEstimado");
                                busList.add(new Bus_stop(codigoBus, nombreBus, tiempoEstimado, tiempoEstimado));
                            }

                            busAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error al procesar los datos de la parada", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);// Detener el temporizador cuando se destruye la vista

    }


    @Override
    public void onResume() {
        super.onResume();
        // Establece el título del fragmento de búsqueda cuando se reanuda
        getActivity().setTitle("Detalle de Parada");
    }

}

/*
    private void actualizarParada(String codigo) {
        if (codigo.equals("109")) {
            stopCodeTextView.setText("109");
            stopNameTextView.setText("BANCO DE LA NACION");
            busList.clear();
            busList.add(new Bus_stop("AN-07", "ALIMENTADOR BELAUNDE", 200, 400));
            busList.add(new Bus_stop("AN-08", "ALIMENTADOR MILAGROS DE JESUS", 200, 400));
            busList.add(new Bus_stop("AN-09", "ALIMENTADOR CARABAYLLO", 200, 400));
            busList.add(new Bus_stop("AN-04", "ALIMENTADOR COLLIQUE", 80, 280));
        } else if (codigo.equals("110")) {
            stopCodeTextView.setText("110");
            stopNameTextView.setText("ESPAÑA");
            busList.clear();
            busList.add(new Bus_stop("AN-04", "ALIMENTADOR COLLIQUE", 120, 320));
            busList.add(new Bus_stop("AN-06", "ALIMENTADOR PUNO", 200, 400));
        } else if (codigo.equals("111")) {
        stopCodeTextView.setText("111");
        stopNameTextView.setText("LA CINCUENTA");
        busList.clear();
        busList.add(new Bus_stop("AN-04", "ALIMENTADOR COLLIQUE", 120, 320));
            busList.add(new Bus_stop("AN-09", "ALIMENTADOR CARABAYLLO", 200, 400));
            busList.add(new Bus_stop("AN-06", "ALIMENTADOR BELAUNDE", 200, 400));
        } else if (codigo.equals("112")) {
            stopCodeTextView.setText("112");
            stopNameTextView.setText("GRIFO AÑO NUEVO");
            busList.clear();
            busList.add(new Bus_stop("AN-04", "ALIMENTADOR COLLIQUE", 120, 320));
            busList.add(new Bus_stop("AN-08", "ALIMENTADOR MILAGROS DE JESUS", 200, 400));
        } else if (codigo.equals("113")) {
            stopCodeTextView.setText("113");
            stopNameTextView.setText("LA PASCANA");
            busList.clear();
            busList.add(new Bus_stop("AN-08", "ALIMENTADOR MILAGROS DE JESUS", 120, 320));
            busList.add(new Bus_stop("AN-09", "ALIMENTADOR CARABAYLLO", 200, 400));
        } else if (codigo.equals("114")) {
            stopCodeTextView.setText("114");
            stopNameTextView.setText("BELAUNDE");
            busList.clear();
            busList.add(new Bus_stop("AN-06", "ALIMENTADOR BELAUNDE", 120, 320));
        }
        else if (codigo.equals("115")) {
            stopCodeTextView.setText("115");
            stopNameTextView.setText("MERCADO CHACRA CERRO");
            busList.clear();
            busList.add(new Bus_stop("AN-04", "ALIMENTADOR COLLIQUE", 120, 320));
            busList.add(new Bus_stop("AN-09", "ALIMENTADOR CARABAYLLO", 200, 400));
        }
        else {
            stopCodeTextView.setText("No encontrado");
            stopNameTextView.setText("Parada no encontrada");
            busList.clear();
        }

        busAdapter.notifyDataSetChanged();
    }
*/
