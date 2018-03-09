package com.example.rxexamplesearch.Data;

import com.example.rxexamplesearch.POJO.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Karthi on 9/3/2018.
 */

public interface ApiService {

    @GET("api")
    Single<User> getUsers(@Query("results") String source);
}
