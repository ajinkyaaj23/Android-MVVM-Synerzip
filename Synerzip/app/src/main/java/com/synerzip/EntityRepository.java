package com.synerzip;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EntityRepository {

    private EntityDao entityDao;
    private LiveData<List<Entity>> allEntities;

    private RequestQueue mQueue;
    private MutableLiveData<List<Entity>> allEntitiesFromAPI;
    private Application application;

    public EntityRepository(Application application){

        DatabaseManager database = DatabaseManager.getInstance(application);
        entityDao = database.entityDao();

        allEntities = entityDao.getAllEntities();
        mQueue = VolleySingleton.getInstance(application).getRequestQueue();
        this.application = application;
    }

    public void insert(Entity entity){
        new InsertEntityAsyncTask(entityDao).execute(entity);
    }

    public void update(Entity entity){
        new UpdateEntityAsyncTask(entityDao).execute(entity);
    }

    public void delete(Entity entity){
        new DeleteEntityAsyncTask(entityDao).execute(entity);
    }

    public void deleteAllEntites(){
        new DeleteAllEntitiesAsyncTask(entityDao).execute();
    }

    public LiveData<List<Entity>> getAllEntities(){
        return allEntities;
    }


    private static class InsertEntityAsyncTask extends AsyncTask<Entity, Void, Void> {

        private EntityDao entityDao;

        private InsertEntityAsyncTask(EntityDao entityDao){
            this.entityDao = entityDao;
        }

        @Override
        protected Void doInBackground(Entity... entities) {
            entityDao.insert(entities[0]);
            return null;
        }
    }

    private static class UpdateEntityAsyncTask extends AsyncTask<Entity, Void, Void> {

        private EntityDao entityDao;

        private UpdateEntityAsyncTask(EntityDao entityDao){
            this.entityDao = entityDao;
        }

        @Override
        protected Void doInBackground(Entity... entities) {
            entityDao.update(entities[0]);
            return null;
        }
    }

    private static class DeleteEntityAsyncTask extends AsyncTask<Entity, Void, Void> {

        private EntityDao entityDao;

        private DeleteEntityAsyncTask(EntityDao entityDao){
            this.entityDao = entityDao;
        }

        @Override
        protected Void doInBackground(Entity... entities) {
            entityDao.delete(entities[0]);
            return null;
        }
    }

    private static class DeleteAllEntitiesAsyncTask extends AsyncTask<Void, Void, Void> {

        private EntityDao entityDao;

        private DeleteAllEntitiesAsyncTask(EntityDao entityDao){
            this.entityDao = entityDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            entityDao.deleteAllNotes();
            return null;
        }
    }

    public MutableLiveData<List<Entity>> callGetEntities() {

        if (allEntitiesFromAPI == null) {
            allEntitiesFromAPI = new MutableLiveData<List<Entity>>();
            loadDataFromAPI();
            return allEntitiesFromAPI;

        } else
            return allEntitiesFromAPI;
    }

    private void loadDataFromAPI (){

        String url = "https://itunes.apple.com/us/rss/newfreeapplications/limit=2/json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Toast.makeText(application,response.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject feedObject = response.getJSONObject("feed");
                            JSONArray entriesArray = feedObject.getJSONArray("entry");
                            List<Entity> entityList = new ArrayList<>();
                            for(int i=0; i < entriesArray.length(); i++){
                                Entity entity = new Entity();
                                entity.setName(entriesArray.getJSONObject(i).getJSONObject("im:name").getString("label"));
                                entity.setRights(entriesArray.getJSONObject(i).getJSONObject("rights").getString("label"));
                                entity.setTitle(entriesArray.getJSONObject(i).getJSONObject("title").getString("label"));
                                entity.setArtist(entriesArray.getJSONObject(i).getJSONObject("im:artist").getString("label"));
                                entity.setImage(entriesArray.getJSONObject(i).getJSONArray("im:image").getJSONObject(0).getString("label"));
                                entityList.add(entity);
                                insert(entity);
                            }
                            allEntitiesFromAPI.setValue(entityList);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(application,"Error in API call", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);

    }


    /*private void loadDataFromAPI() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIClient api = retrofit.create(APIClient.class);
        Call<List<Feed>> call = api.callGetEntities();

        call.enqueue(new Callback<List<Feed>>() {
            @Override
            public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {

                //finally we are setting the list to our MutableLiveData
                List<Feed> data = (response.body());
                Toast.makeText(application,data.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Feed>> call, Throwable t) {
                String str = t.getMessage();
            }
        });
    }*/


}
