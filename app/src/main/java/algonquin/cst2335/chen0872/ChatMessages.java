package algonquin.cst2335.chen0872;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Timer;

@Entity
public class ChatMessages {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="message")
    String message;
    @ColumnInfo(name="TimeSent")
    String timeSent;
    @ColumnInfo(name="isSentButton")
    boolean isSentButton;


    ChatMessages(String m, String t, boolean isSent)
    {
        message = m;
        timeSent = t;
        isSentButton = isSent;
    }

    ChatMessages(){

    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean getIsSentButton(){
        return isSentButton;
    }
}
