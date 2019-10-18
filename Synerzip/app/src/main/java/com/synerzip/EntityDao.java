package com.synerzip;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntityDao {

    @Insert
    void insert(Entity entity);

    @Update
    void update(Entity entity);

    @Delete
    void delete(Entity entity);

    @Query("DELETE FROM entity_table")
    void deleteAllNotes();

    @Query("SELECT * FROM entity_table ORDER BY id DESC")
    LiveData<List<Entity>> getAllEntities();


}
