package empresa.android.metgo_encarnacion.ui.mapadeparadas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapaDeParadasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MapaDeParadasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mapa de paradas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}