package empresa.android.metgo_encarnacion.ui.busqueda;

import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.List;

import empresa.android.metgo_encarnacion.Parada;
import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentBusquedaBinding;

import empresa.android.metgo_encarnacion.ui.busqueda.BusquedaViewModel;
import empresa.android.metgo_encarnacion.ui.detalleparada.DetalleparadaFragment;

public class BusquedaFragment extends Fragment {

    private EditText codigoParadaEditText;
    private ImageButton buscarButton;

    private FragmentBusquedaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BusquedaViewModel busquedaViewModel =
                new ViewModelProvider(this).get(BusquedaViewModel.class);

        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_busqueda,container,false);

        codigoParadaEditText = vista.findViewById(R.id.codigoParadaEditText);
        buscarButton = vista.findViewById(R.id.buscarButton);

        // Configurar EditText para entrada numérica y lupa en teclado
        codigoParadaEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        codigoParadaEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        // Configurar el listener del botón de búsqueda
        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCodigoParada();
            }
        });

        // Configurar el listener para la acción de búsqueda en el teclado (lupa)
        codigoParadaEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    buscarCodigoParada();
                    return true;
                }
                return false;
            }
        });

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Establece el título del fragmento de búsqueda cuando se reanuda
        getActivity().setTitle("Busqueda");
    }


    private void buscarCodigoParada() {
        String codigoParada = codigoParadaEditText.getText().toString().trim();
        if (!codigoParada.isEmpty()) {
            mostrarDetalleParada(codigoParada);
        } else {
            Toast.makeText(getContext(), "Ingrese un código de parada", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDetalleParada(String codigoParada) {
        DetalleparadaFragment detalleParadaFragment = DetalleparadaFragment.newInstance(codigoParada);

        // Obtener el FragmentManager y comenzar la transacción
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplazar el fragmento actual con el detalle de la parada
        transaction.replace(R.id.drawer_layout, detalleParadaFragment); //  ID correcto del contenedor
        transaction.addToBackStack(null); // Añadir a la pila de retroceso si quieres que se pueda volver
        transaction.commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

/*  private void buscarParadaPorCodigo(String codigoParada) {
        // Buscar la parada en la lista de paradas
        for (Parada parada : listaParadas) {
            if (parada.getCodigoParada().equals(codigoParada)) {
                // Si se encuentra la parada, abrir el DetalleParadaFragment con los datos de la parada
                DetalleparadaFragment detalleParadaFragment = DetalleparadaFragment.newInstance(parada.getCodigoParada(), parada.getNombreParada());

                // Iniciar el fragmento de detalles
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detalleParadaFragment) // Asegúrate de usar el ID correcto
                        .addToBackStack(null)
                        .commit();
                return;
            }
        }

        // Si no se encuentra la parada
        Toast.makeText(getContext(), "No se encontró la parada con el código " + codigoParada, Toast.LENGTH_SHORT).show();
    }*/



 /*  // Navegar al DetalleParadaFragment
                    DetalleparadaFragment detalleParadaFragment = DetalleparadaFragment.newInstance(codigoParada);
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmen, detalleParadaFragment); // Cambia el contenedor según tu layout
                    transaction.addToBackStack(null); // Agrega a la pila de retroceso
                    transaction.commit();*/