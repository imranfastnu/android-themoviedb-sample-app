package com.themoviedb.app.http;

/**
 * Created by imran.khalid on 7/24/2016.
 */
public class Constants {


    public static String API_KEY="fce07d073a30fe669dc6e4798ac00567";
    private static String API_END_POINT="https://api.themoviedb.org/3/";

    //Images resolutions supported (w185, w300, w342, w500)
    public static String IMAGE_BASE_PATH="https://image.tmdb.org/t/p/w185";
    public static String IMAGE_BASE_PATH_W300="https://image.tmdb.org/t/p/w300";

    //Movie Filter
    public static String MIN_YEAR_FILTER="&primary_release_date.gte=";
    public static String MAX_YEAR_FILTER="&primary_release_date.lte=";

    public static String GET_MOVIES = API_END_POINT+ "discover/movie?api_key="+ API_KEY+ "&sort_by=popularity.desc";

}
