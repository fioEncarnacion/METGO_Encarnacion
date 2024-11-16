package empresa.android.metgo_encarnacion.ui.noticias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentNoticiasBinding;
import empresa.android.metgo_encarnacion.ui.noticias.NoticiasViewModel;

public class NoticiasFragment extends Fragment implements View.OnClickListener {

    private Button btn_url;
    String url="https://www.gob.pe/institucion/atu/noticias";

    private FragmentNoticiasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NoticiasViewModel noticiasViewModel =
                new ViewModelProvider(this).get(NoticiasViewModel.class);

        View root=inflater.inflate(R.layout.fragment_noticias,container,false);
        btn_url=root.findViewById(R.id.btn_link);
        btn_url.setOnClickListener(this);

        return root;

    }

    public void onClick(View v){
       Uri _link=Uri.parse(url);
       Intent i=new Intent(Intent.ACTION_VIEW,_link);
       startActivity(i);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}