package empresa.android.metgo_encarnacion.ui.noticias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoticiasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NoticiasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is noticias fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}