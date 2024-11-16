package empresa.android.metgo_encarnacion.ui.acercade;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AcercaDeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AcercaDeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is acerca de fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}