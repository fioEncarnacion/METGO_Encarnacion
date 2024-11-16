package empresa.android.metgo_encarnacion.ui.sugerencia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SugerenciaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SugerenciaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}