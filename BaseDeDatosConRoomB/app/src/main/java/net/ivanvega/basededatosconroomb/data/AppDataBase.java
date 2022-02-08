package net.ivanvega.basededatosconroomb.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class} , version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao getUserDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getDataBaseInstance(Context context){
        if(INSTANCE == null){
            synchronized (AppDataBase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "user_database")
                            .build();
                }
            }

        }
        return INSTANCE;
    }




}
