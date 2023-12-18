package algonquin.cst2335.chen0872;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChatMessages.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract ChatMessagesDAO cmDAO();
}
