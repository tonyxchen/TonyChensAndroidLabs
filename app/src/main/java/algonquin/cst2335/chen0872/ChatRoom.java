package algonquin.cst2335.chen0872;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.chen0872.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.chen0872.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ArrayList<ChatMessages> messages;
    ChatRoomViewModel chatModel ;
    ChatMessagesDAO mDAO;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        messages =  chatModel.messages.getValue();

        if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(clk -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessages currentMessage = new ChatMessages(binding.textInput.getText().toString(), currentDateandTime, true);
            messages.add(currentMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {mDAO.insertMessage(currentMessage);});
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(clk -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessages currentMessage = new ChatMessages(binding.textInput.getText().toString(), currentDateandTime, false);
            messages.add(currentMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {mDAO.insertMessage(currentMessage);});
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

            itemView.setOnClickListener(clk ->{
                int position = getAbsoluteAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete this message: " +messageText.getText())
                    .setTitle("Question: ")
                    .setPositiveButton("Yes",(dialog, cl)->{
                        ChatMessages m = messages.get(position);
                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() -> {mDAO.deleteMessage(m);});
                        messages.remove(position);
                        myAdapter.notifyItemRemoved(position);
                        ChatMessages removedMessage = m;

                        Snackbar.make(messageText, "You deleted #" + position, Snackbar.LENGTH_LONG)
                                .setAction("UNDO", clik -> {
                                    messages.add(position, removedMessage);
                                    myAdapter.notifyItemInserted(position);
                                })
                                .show();
                    })
                .setNegativeButton("No",(dialog, cl)->{})
                .create().show();
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }




}