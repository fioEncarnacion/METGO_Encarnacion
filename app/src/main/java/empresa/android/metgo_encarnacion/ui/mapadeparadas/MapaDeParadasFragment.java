package empresa.android.metgo_encarnacion.ui.mapadeparadas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentMapaDeParadasBinding;


public class MapaDeParadasFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapaDeParadasBinding binding;
    private GoogleMap mMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inicializa el ViewModel
        MapaDeParadasViewModel mapadeparadasViewModel =
                new ViewModelProvider(this).get(MapaDeParadasViewModel.class);

        // Usa View Binding para inflar el layout
        View vista=inflater.inflate(R.layout.fragment_mapa_de_paradas,container,false);

        // Configura el SupportMapFragment para inicializar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return vista;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Configura el mapa, por ejemplo, añade un marcador con las nuevas coordenadas
        LatLng comas = new LatLng(-11.93316403333439, -77.04614469171912);
        mMap.addMarker(new MarkerOptions().position(comas).title("comas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(comas, 15));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
