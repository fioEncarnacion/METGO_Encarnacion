package empresa.android.metgo_encarnacion.ui.paradas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParadasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ParadasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is paradas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}