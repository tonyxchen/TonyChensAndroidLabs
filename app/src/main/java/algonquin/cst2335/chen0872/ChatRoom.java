package algonquin.cst2335.chen0872;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.chen0872.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.chen0872.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ArrayList<ChatMessages> messages;
    ChatRoomViewModel chatModel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        messages =  chatModel.messages.getValue();

        if(messages == null) {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessages>());
        }

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(clk -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessages currentMessage = new ChatMessages(binding.textInput.getText().toString(), currentDateandTime, true);
            messages.add(currentMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(clk -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessages currentMessage = new ChatMessages(binding.textInput.getText().toString(), currentDateandTime, false);
            messages.add(currentMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });


        binding.recycleView.setAdapter(myAdapter= new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                if(viewType == 1){
                    View receive = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_message, parent, false);
                    return new MyRowHolder(receive);
                } else {
                    View send = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message, parent, false);
                    return new MyRowHolder(send);
                }
            }

            public void onBindViewHolder(@NonNull MyRowHolder holder, int position){
                holder.messageText.setText("");
                holder.timeText.setText("");
                String me = messages.get(position).getMessage();
                String ti = messages.get(position).getTimeSent();
                holder.messageText.setText(me);
                holder.timeText.setText(ti);

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                if(messages.get(position).getIsSentButton()){
                    return 0;
                }else {
                    return 1;
                }
            }
        });

    }
    class MyRowHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }




}