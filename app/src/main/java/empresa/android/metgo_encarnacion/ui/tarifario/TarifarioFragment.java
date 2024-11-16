package empresa.android.metgo_encarnacion.ui.tarifario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentTarifarioBinding;


public class TarifarioFragment extends Fragment {

    private FragmentTarifarioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TarifarioViewModel tarifarioViewModel =
                new ViewModelProvider(this).get(TarifarioViewModel.class);

        // Inflar el diseño del fragmento y obtener la vista
        View root = inflater.inflate(R.layout.fragment_tarifario, container, false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}