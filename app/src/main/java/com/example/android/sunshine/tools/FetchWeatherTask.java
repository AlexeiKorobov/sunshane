package com.example.android.sunshine.tools;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alex on 08.05.17.
 */

public class FetchWeatherTask extends AsyncTask<String, Void, List<String>> {
    private final String TAG = FetchWeatherTask.class.getSimpleName();

    @Override
    protected List<String> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        String QUERY_PARAM = "q";
        String UNITS_PARAM = "units";
        String FORMAT_PARAM = "mode";
        String DAYS_PARAM = "cnt";
        String APPID_PARAM = "APPID";
        int NUMBER_DAYS = 7;

        List<String > result = null;
        try {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.openweathermap.org")
                    .appendPath("data/2.5/forecast/daily")
                    .appendQueryParameter(QUERY_PARAM, params[0])
                    .appendQueryParameter(UNITS_PARAM, "metric")
                    .appendQueryParameter(FORMAT_PARAM, "json")
                    .appendQueryParameter(DAYS_PARAM, String.valueOf(NUMBER_DAYS))
                    .appendQueryParameter(APPID_PARAM, "42e9eb2ebe80b2fdbfd2edc45ce1715a")
                    .build();

            URL url = new URL(builder.toString());
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (Exception e) {
            Log.e(TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        try {
            result = Arrays.asList(
                    new WeatherJsonParser().getWeatherDataFromJson(forecastJsonStr, NUMBER_DAYS)
            );
        } catch (JSONException ex){
            Log.e(TAG, "Error JSON parsing", ex);
        }
        return result;
    }
}
