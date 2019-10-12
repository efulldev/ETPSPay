package com.efulltech.epay_tps_library_module;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CardRemovedFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("Card Removed.");
//
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                getActivity().finish();
//                Toast.makeText(getActivity(), "Card Removed", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return builder.create();



        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

//        Button cancelBtn =  mView.findViewById(R.id.cancelBtn);
//        Button changeBtn =  mView.findViewById(R.id.proceedBtn);


        builder.setView(mView);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        getActivity().finish();
    }
}
