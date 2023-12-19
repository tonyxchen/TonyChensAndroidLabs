package algonquin.cst2335.chen0872;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ChatMessages>> messages = new MutableLiveData<>();

    public MutableLiveData<ChatMessages> selectedMessage = new MutableLiveData< >();
}
