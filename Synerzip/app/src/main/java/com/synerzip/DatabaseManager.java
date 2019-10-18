package com.synerzip;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Entity.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    private static DatabaseManager instance;

    public abstract EntityDao entityDao();

    public static synchronized DatabaseManager getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseManager.class,"entity_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private EntityDao noteDao;

        private PopulateDbAsyncTask(DatabaseManager db){
            noteDao = db.entityDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Entity(1,"Ajinkya","myRights", "25.00", "https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/f3/13/da/f313dac4-a9a2-63d6-3826-d19eab674061/AppIcon-0-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-7.png/53x53bb.png",
                    "artist", "title", "link", "appId", "mime jpg", "no category", "17-10-2019"));
            noteDao.insert(new Entity(2,"Ashok","myRights", "25.00", "https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/f3/13/da/f313dac4-a9a2-63d6-3826-d19eab674061/AppIcon-0-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-7.png/53x53bb.png",
                    "artist", "title", "link", "appId", "mime jpg", "no category", "17-10-2019"));
            noteDao.insert(new Entity(3,"Jagadale","myRights", "25.00", "https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/f3/13/da/f313dac4-a9a2-63d6-3826-d19eab674061/AppIcon-0-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-7.png/53x53bb.png",
                    "artist", "title", "link", "appId", "mime jpg", "no category", "17-10-2019"));
            noteDao.insert(new Entity(4,"Satara","myRights", "25.00", "https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/f3/13/da/f313dac4-a9a2-63d6-3826-d19eab674061/AppIcon-0-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-7.png/53x53bb.png",
                    "artist", "title", "link", "appId", "mime jpg", "no category", "17-10-2019"));
            return null;
        }
    }

}
