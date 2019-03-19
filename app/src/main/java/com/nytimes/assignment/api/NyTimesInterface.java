package com.nytimes.assignment.api;

import com.nytimes.assignment.model.Article;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NyTimesInterface {

    @GET("svc/mostpopular/v2/viewed/{period}.json?}")
    Call<Article> getAllArticles(@Path("period") int period, @Query(value = "api-key", encoded = true) String apiKey);
}