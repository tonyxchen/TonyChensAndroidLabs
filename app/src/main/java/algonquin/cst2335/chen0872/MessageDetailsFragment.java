package algonquin.cst2335.chen0872;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;

import algonquin.cst2335.chen0872.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

    ChatMessages selected;

    public MessageDetailsFragment(ChatMessages m){
        selected = m;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);



        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.message.setText(selected.message);
        binding.time.setText(selected.timeSent);
        if(selected.isSentButton){
            binding.sendReceiveDetails.setText("Sent Message");
        } else {
            binding.sendReceiveDetails.setText("Received Message");
        }

        binding.databaseID.setText("Id = " + selected.id);
        return binding.getRoot();






    }

}
