package uz.mobiler.wallpaperappg2122.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kotlin.jvm.Synchronized;
import uz.mobiler.wallpaperappg2122.room.dao.ImageDao;
import uz.mobiler.wallpaperappg2122.room.entity.ImageEntity;

@Database(entities = {ImageEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ImageDao imageDao();

    public static AppDatabase instance;

    @Synchronized
    public static AppDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
