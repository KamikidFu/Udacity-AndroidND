package me.kamikid.news.activity;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import me.kamikid.news.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NEWSPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference topics = findPreference(getString(R.string.settings_topics_key));
            bindPreferenceSummaryToValue(topics);

            Preference fromDate =findPreference(getString(R.string.settings_from_date_key));
            bindPreferenceSummaryToValue(fromDate);

            Preference toDate = findPreference(getString(R.string.settings_to_date_key));
            bindPreferenceSummaryToValue(toDate);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if(preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if(prefIndex>=0){
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }else{
                preference.setSummary(stringValue);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
