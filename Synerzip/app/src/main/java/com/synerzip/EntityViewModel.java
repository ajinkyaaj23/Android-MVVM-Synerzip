package com.synerzip;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


public class EntityViewModel extends AndroidViewModel {

    private EntityRepository repository;
    private LiveData<List<Entity>> allEntities;
    private MutableLiveData<List<Entity>> allEntitiesFromAPI;
    private Application application;


    public EntityViewModel(@NonNull Application application) {
        super(application);
        repository = new EntityRepository(application);
        allEntities = repository.getAllEntities();
        this.application = application;
    }

    public void insert(Entity entity) {
        repository.insert(entity);
    }

    public void update(Entity entity) {
        repository.update(entity);
    }

    public void delete(Entity entity) {
        repository.delete(entity);
    }

    public void deleteallEntities() {
        repository.deleteAllEntites();
    }

    public LiveData<List<Entity>> getallEntities() {
        return allEntities;
    }

    public LiveData<List<Entity>> callGetEntities() {

        if (allEntitiesFromAPI == null) {
            allEntitiesFromAPI = new MutableLiveData<List<Entity>>();
            allEntitiesFromAPI = repository.callGetEntities();
            return allEntitiesFromAPI;

        } else
            return allEntitiesFromAPI;
    }


}
