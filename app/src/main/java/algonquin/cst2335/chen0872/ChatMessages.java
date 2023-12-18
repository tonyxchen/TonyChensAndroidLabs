package algonquin.cst2335.chen0872;

public class ChatMessages {


    String message;
    String timeSent;
    boolean isSentButton;


    ChatMessages(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSentButton = sent;
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
