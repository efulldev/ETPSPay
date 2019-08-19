package com.efulltech.etpspay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.MyViewHolder> {

    private Context context;
//    private List<Transaction> notesList;
//    private mainDatabase mDatabase;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView refno;
        public TextView note;
        public TextView timestamp;
        public TextView response;
        public TextView appName;

        public MyViewHolder(View view) {
            super(view);
            refno = view.findViewById(R.id.refno);
            note = view.findViewById(R.id.note);
            timestamp = view.findViewById(R.id.timestamp);
            response = view.findViewById(R.id.response);
            appName = view.findViewById(R.id.appNameText);
        }
    }


//    public TransactionHistoryAdapter(Context context, List<Transaction> notesList, mainDatabase mDatabase) {
//        this.context = context;
//        this.notesList = notesList;
//        this.mDatabase = mDatabase;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transact_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Transaction note = notesList.get(position);
//        Float _amount = Float.parseFloat(note.getAmount());
//        _amount = _amount.floatValue();
//        String _msg = formatAmount(_amount);
//        String _response = note.getResponsemessage();
//        String _dateTime = note.getDate()+" "+note.getTime();
//        String appName = mDatabase.getTransactionOrigin(note.getRefno());

//        display ref no
//        holder.refno.setText("RRN: "+note.getRefno());
//        display response message
//        holder.note.setText(_msg);
////        display response message
//        holder.response.setText(_response);
//        // Formatting and displaying timestamp
//        holder.timestamp.setText(_dateTime);
////        display app name from which transaction was started from
//        holder.appName.setText(appName);
    }



    @Override
    public int getItemCount() {
//        return notesList.size();
        return getItemCount();
    }

}