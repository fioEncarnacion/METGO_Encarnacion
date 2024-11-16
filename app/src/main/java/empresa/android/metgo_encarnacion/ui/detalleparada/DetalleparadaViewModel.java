package empresa.android.metgo_encarnacion.ui.detalleparada;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetalleparadaViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DetalleparadaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}