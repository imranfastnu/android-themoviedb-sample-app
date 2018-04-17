package com.themoviedb.app.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.themoviedb.app.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imran.khalid on 7/24/2016.
 */
public class ResponseHandler {

    public static List<Movie> parseMovies(JSONObject body) {

        List<Movie> dataList = new ArrayList<>();

        try {
            JSONArray list = body.getJSONArray("results");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Movie>>() {}.getType();
            dataList = gson.fromJson(list.toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataList;

    }
}
