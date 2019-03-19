package com.nytimes.assignment.api;

import com.nytimes.assignment.model.Article;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.nytimes.assignment.api.NyTimesClient.getClient;

public class ApiManager {

    private final Retrofit client;
    private final String apiKey = "QvxnkmoMHL8yAocVdzOmmgu6IMORZdWY";

    public ApiManager() {
        client = getClient();
    }

    public void getArticles(int period,final ApiCallback<Article> callback) {
        NyTimesInterface services = client.create(NyTimesInterface.class);
        services.getAllArticles(period, apiKey).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()) {
                    callback.success(response.body());
                } else {
                    callback.failure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                callback.failure(500);
            }
        });
    }


    public interface ApiCallback<T> {
        void success(T response);
        void failure(int responseCode);
    }
}


