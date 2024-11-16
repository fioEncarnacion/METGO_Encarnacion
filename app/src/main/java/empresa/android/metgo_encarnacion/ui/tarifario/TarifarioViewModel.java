package empresa.android.metgo_encarnacion.ui.tarifario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TarifarioViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> mText;

    public TarifarioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tarifario fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}