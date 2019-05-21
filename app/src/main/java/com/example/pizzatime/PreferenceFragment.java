package com.example.pizzatime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;


public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.resources);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        int count = preferenceScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference pref = preferenceScreen.getPreference(i);

            if(!(pref instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(pref.getKey(), "standard");

                if (pref instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) pref;
                    int prefIndex = listPreference.findIndexOfValue(value);
                    if (prefIndex >= 0) {
                        listPreference.setSummary(listPreference.getEntries()[prefIndex]);
                    }
                }
            }
        }
    }
}