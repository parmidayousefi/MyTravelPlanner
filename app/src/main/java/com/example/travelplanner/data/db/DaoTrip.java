package com.example.travelplanner.data.db;
import androidx.room.*;
import java.util.List;
@Dao
public interface DaoTrip {
    @Insert long insertTrip(Trip t);
    @Insert long insertPlace(Place p);
    @Insert long insertTodo(Todo t);
    @Insert long insertPhoto(Photo p);
    @Query("SELECT * FROM trips WHERE uid = :uid ORDER BY id DESC")
    List<Trip> getTrips(String uid);
    @Query("SELECT * FROM places WHERE trip_id = :tripId")
    List<Place> getPlaces(long tripId);
    @Query("SELECT * FROM todos WHERE trip_id = :tripId")
    List<Todo> getTodos(long tripId);
    @Query("SELECT * FROM photos WHERE trip_id = :tripId")
    List<Photo> getPhotos(long tripId);
    @Query("UPDATE todos SET done = :done WHERE id = :id")
    void updateTodoDone(long id, boolean done);
    @Query("DELETE FROM todos WHERE id = :id")
    void deleteTodo(long id);
    @Query("DELETE FROM trips WHERE id = :tripId")
    void deleteTrip(long tripId);
    @Query("DELETE FROM places WHERE trip_id = :tripId")
    void deletePlaces(long tripId);
    @Query("DELETE FROM photos WHERE trip_id = :tripId")
    void deletePhotos(long tripId);
}
