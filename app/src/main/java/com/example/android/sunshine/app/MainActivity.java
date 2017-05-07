package com.example.android.sunshine.app;

import android.content.Intent;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //EditText mEdit   = (EditText)findViewById(R.id.editText);
        //String mText = mEdit.getText().toString();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            /*
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mText);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            */
            return true;
        }
        else if(id == R.id.action_email)
        {
            /*
            Intent emailIntent = new Intent();
            emailIntent.setAction(Intent.ACTION_SEND);
            emailIntent.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "someone@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, mText);
            emailIntent.setType("text/plain");
            startActivity(emailIntent);
            */
            return true;
        }
        else if(id == R.id.action_get_contact)
        {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        ArrayAdapter<String> mAdapter;
        List<String> mfakeList;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //fake data for the adapter
            mfakeList = new ArrayList<>();
            for (int i = 0; i < 50; i++){
                mfakeList.add("Forecast: " + i);
            }

            mAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_forecast,
                            R.id.list_item_forecast_text_view,
                            mfakeList);

            ListView list = (ListView)rootView.findViewById(R.id.list_view_forecast);
            list.setAdapter(mAdapter);

            return rootView;
        }
    }
}
