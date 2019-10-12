package com.efulltech.epay_tps_library_module;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {

    private TextView title, message;
    private Button positiveBtn;

    public AlertDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AlertDialogFragment newInstance(String title, String message, Boolean positive) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        args.putBoolean("positive", positive);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        title = (TextView) view.findViewById(R.id.dialogTitle);
        message = (TextView) view.findViewById(R.id.dialogMessage);
        positiveBtn = (Button) view.findViewById(R.id.proceedBtn);
        // Fetch arguments from bundle and set title
        String _title = null;
        Boolean positive = getArguments().getBoolean("positive", false);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1) {
            _title = getArguments().getString("title", "Alert");
            String _message = getArguments().getString("message", "");
            title.setText(_title);
            message.setText(_message);
        }
        if(!positive){
            positiveBtn.setVisibility(view.GONE);
        }

        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }



    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        getActivity().finish();
    }
}
