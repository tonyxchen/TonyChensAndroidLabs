package algonquin.cst2335.chen0872;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessagesDAO {
    @Insert
    public long insertMessage(ChatMessages m);

    @Query("SELECT * FROM ChatMessages")
    public List<ChatMessages> getAllMessages();

    @Delete
    void deleteMessage(ChatMessages m);
}
