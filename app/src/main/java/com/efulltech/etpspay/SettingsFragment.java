package com.efulltech.etpspay;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    private EditTextPreference operator, supervisor, admin;
    private Preference EOD, pEod, rPrintReceipt, pTransactionHistory, closeBatch, clearDatabase;
    private String userPermission;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_pref);

        operator = getPreferenceManager().findPreference(getString(R.string.pref_operator_pin_key));
        supervisor = getPreferenceManager().findPreference(getString(R.string.pref_supervisor_pin_key));
        admin = getPreferenceManager().findPreference(getString(R.string.pref_admin_pin_key));

        //        get bundles from previous activity
//        use data to set visibility of options which should be
//        accessible to users based on their previledge
//        Intent intent = getIntent();
//        final String permission = intent.getStringExtra("permission");
//        userPermission = permission;
//disable some functions based on permission level
//        showSettingsButtons(userPermission);


        assert operator != null;
        operator.setOnBindEditTextListener(editText ->
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));

        assert supervisor != null;
        supervisor.setOnBindEditTextListener(editText ->
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));

        assert admin != null;
        admin.setOnBindEditTextListener(editText ->
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));

        EOD = findPreference(getString(R.string.pref_end_of_day_key));
        pEod = findPreference(getString(R.string.pref_print_end_of_day_key));
        rPrintReceipt = findPreference(getString(R.string.pref_re_print_receipt_key));
        pTransactionHistory = findPreference(getString(R.string.pref_transaction_history_key));
        closeBatch = findPreference(getString(R.string.pref_close_batch_key));
        clearDatabase = findPreference(getString(R.string.pref_clear_database_key));
//        EOD = findPreference(getString(R.string.pref_end_of_day_key));
//        pEod = findPreference(getString(R.string.pref_print_end_of_day_key));

        assert EOD != null;
        EOD.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "Clicked EOD", Toast.LENGTH_SHORT).show();
            return true;
        });

        assert pEod != null;
        pEod.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "Clicked pEOD", Toast.LENGTH_SHORT).show();
            return true;
        });

        assert rPrintReceipt != null;
        rPrintReceipt.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "Clicked rPrintReceipt", Toast.LENGTH_SHORT).show();
            return true;
        });

        assert pTransactionHistory != null;
        pTransactionHistory.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "Clicked pTransactionHistory", Toast.LENGTH_SHORT).show();
            return true;
        });

        assert closeBatch != null;
        closeBatch.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "Clicked closeBatch", Toast.LENGTH_SHORT).show();
            return true;
        });

        assert clearDatabase != null;
        clearDatabase.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "Clicked clearDatabase", Toast.LENGTH_SHORT).show();
            return true;
        });


    }



}
