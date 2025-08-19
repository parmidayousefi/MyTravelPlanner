package com.example.travelplanner.data.db;
import android.content.Context;
import androidx.room.*;
@Database(entities={Trip.class, Place.class, Todo.class, Photo.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoTrip dao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase get(Context ctx){
        if(INSTANCE==null){
            synchronized (AppDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, "travelplanner.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
