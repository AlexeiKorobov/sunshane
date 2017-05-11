package com.example.android.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.sunshine.tools.FetchWeatherTask;


import java.util.List;


/**
 * Created by alex on 08.05.17.
 */

public  class ForeCastFragment extends Fragment {
    private  final String TAG = ForeCastFragment.class.getSimpleName();

    ArrayAdapter<String> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //TODO read about states https://developer.android.com/guide/topics/resources/runtime-changes.html
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            weatherUpdate();
        } catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if (id == R.id.action_refresh) {
                weatherUpdate();
                return true;
            }
        } catch (Exception ex){
                Log.e(TAG, ex.toString());
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        try {
            mAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_forecast,
                            R.id.list_item_forecast_text_view);
            ListView list = (ListView)rootView.findViewById(R.id.list_view_forecast);
            list.setAdapter(mAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = mAdapter.getItem(position);
                    //Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), DetailedActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, item);
                    startActivity(intent);
                }
            });
        } catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        return rootView;
    }

    private void weatherUpdate() throws InterruptedException, java.util.concurrent.ExecutionException {
        mAdapter.clear();
        List<String> forecast = getData();
        mAdapter.addAll(forecast);
    }

    private List<String> getData() throws InterruptedException, java.util.concurrent.ExecutionException {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String city = sharedPref.getString(getString(R.string.pref_city_key),
                getString(R.string.pref_city_default));
        String units = sharedPref.getString(getString(R.string.pref_list_units_key),
                getString(R.string.pref_units_default));
        boolean showInF = false;
        if(!units.equals(getString(R.string.pref_units_default))){
            showInF = true;
        }

        List<String> listForecast;
        listForecast = new FetchWeatherTask()
                .execute(city, String.valueOf(showInF))
                .get();
        return listForecast;
    }
}
