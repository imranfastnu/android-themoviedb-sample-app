package com.themoviedb.app.http;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.themoviedb.app.R;
import com.themoviedb.app.utils.InternetConnectionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by imran.khalid on 5/22/2016.
 */
public class ServerConnectionManager {

    Context context;
    String tag;

    public ServerConnectionManager(Context context) {
        this.context = context;
    }

    public ServerConnectionManager(Context context, String tag) {
        this.context = context;
        this.tag=tag;
    }

    public void executeApiRequest(final String url, final Response.Listener<JSONObject> listener, final Response.ErrorListener errorListener) {

        if (!InternetConnectionManager.isConnectingToInternet(context)) {
            Toast.makeText(context, R.string.internet_issue, Toast.LENGTH_LONG).show();
            errorListener.onErrorResponse(null);
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        listener.onResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
                        //String errorStr = error.getMessage();
                        Toast.makeText(context, R.string.server_unreachable, Toast.LENGTH_LONG).show();
                        errorListener.onErrorResponse(error);

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                // headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        if(tag!=null && !tag.equalsIgnoreCase("")){
            jsonObjectRequest.setTag(tag);
        }

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


    public static void cancelVolleyRequest(Context context, String tag){

        if(tag!=null && !tag.equalsIgnoreCase("")){
            VolleySingleton.getInstance(context).getRequestQueue().cancelAll(tag);
        }
    }

}
