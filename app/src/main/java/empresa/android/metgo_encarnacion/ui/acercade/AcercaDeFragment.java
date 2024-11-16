package empresa.android.metgo_encarnacion.ui.acercade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentAcercaDeBinding;
import empresa.android.metgo_encarnacion.ui.acercade.AcercaDeViewModel;

public class AcercaDeFragment extends Fragment {

    private FragmentAcercaDeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AcercaDeViewModel acercaDeViewModel =
                new ViewModelProvider(this).get(AcercaDeViewModel.class);

        // Inflar el diseño del fragmento y obtener la vista
        View root = inflater.inflate(R.layout.fragment_acerca_de, container, false);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}