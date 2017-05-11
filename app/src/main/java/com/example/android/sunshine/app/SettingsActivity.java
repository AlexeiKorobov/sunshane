package com.example.android.sunshine.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Map;

/**
 * Created by alex on 10.05.17.
 */

public class SettingsActivity extends AppCompatActivity {

    //TODO check what does annotation @Nullable mean
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

    //TODO show the current option in the preference.xml
    public static class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener{

        SharedPreferences mSharedPreferences;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            setSummatyToPreference(getString(R.string.pref_city_key));
            setSummatyToPreference(getString(R.string.pref_list_units_key));
        }


        @Override
        public void onResume() {
            super.onResume();

            mSharedPreferences = getPreferenceManager().getSharedPreferences();
            mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                              String key) {

            Preference preference = findPreference(key);
            String value = sharedPreferences.getString(key,"");
            setSummatyToPreference(preference, value);
        }

        /**
         * Method set summory to preference
         * */
        private void setSummatyToPreference(Preference preference, String summary) {
            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list (since they have separate labels/values).
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(summary);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            } else {
                // For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(summary);
            }
        }
        /**
         * Method set summory to preference
         * */
        private void setSummatyToPreference(String key) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Preference preference = findPreference(key);
            String value = sharedPref.getString(key,"");
            setSummatyToPreference(preference, value);
        }
    }
}
