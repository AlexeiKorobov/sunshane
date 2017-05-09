package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
                mAdapter.clear();
                List<String> days = getData();
                mAdapter.addAll(days);
                return true;
            }
        }
        catch (Exception ex){
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

        }
        catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        return rootView;
    }

    private List<String> getData() throws InterruptedException, java.util.concurrent.ExecutionException {
        List<String> forecastJsonStr;
        forecastJsonStr = new FetchWeatherTask()
                .execute("Moscow")
                .get();
        //Log.i(TAG, forecastJsonStr.toString());
        return forecastJsonStr;
    }
}
