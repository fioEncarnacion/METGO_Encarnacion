package empresa.android.metgo_encarnacion.ui.reclamo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReclamoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReclamoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}