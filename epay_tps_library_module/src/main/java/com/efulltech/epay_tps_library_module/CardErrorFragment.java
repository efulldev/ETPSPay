package com.efulltech.epay_tps_library_module;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_CANCELED;

public class CardErrorFragment extends DialogFragment {

    String message;

    public CardErrorFragment(String message) {
        this.message = message;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(message);
        builder.setMessage("Please try again");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
//                getActivity().finish();
            }
        });

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
        Intent _data = new Intent();
        _data.putExtra("response", "Session timeout");
        _data.putExtra("positive", false);
        getActivity().setResult(RESULT_CANCELED, _data);
        getActivity().finish();
//        Toast.makeText(getActivity(), "Session timeout", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
