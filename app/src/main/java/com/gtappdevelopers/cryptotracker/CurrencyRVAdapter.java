package com.gtappdevelopers.cryptotracker;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearning1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
// on below line we are creating our adapter class
// in this class we are passing our array list
// and our View Holder class which we have created.
public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.CurrencyViewfinder> {
    private static final DecimalFormat df2 = new DecimalFormat("#.##");
    private ArrayList<CurrencyModel> currencyModals;
    private final Context context;
    public CurrencyRVAdapter(ArrayList<CurrencyModel> currencyModals, Context context) {
        this.currencyModals = currencyModals;
        this.context = context;
    }
    // below is the method to filter our list.
    public void filterList(ArrayList<CurrencyModel> filtering) {
        // adding filtered list to our
        // array list and notifying data set changed
        currencyModals = filtering;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CurrencyViewfinder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item, parent, false);
        return new CurrencyViewfinder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CurrencyViewfinder holder, int position) {
        // on below line we are setting data to our item of
        // recycler view and all its views.
        CurrencyModel modal = currencyModals.get(position);
        holder.nameTV.setText(modal.getName());
        holder.rateTV.setText("$ " + df2.format(modal.getPrice()));
        holder.symbolTV.setText(modal.getSymbol());
    }
    @Override
    public int getItemCount() {
        // on below line we are returning
        // the size of our array list.
        return currencyModals.size();
    }
    // on below line we are creating our view holder class
    // which will be used to initialize each view of our layout file.
    public static class CurrencyViewfinder extends RecyclerView.ViewHolder {
        private final TextView symbolTV;
        private final TextView rateTV;
        private final TextView nameTV;
        public CurrencyViewfinder(@NonNull View itemView) {
            super(itemView);
            // on below line we are initializing all
            // our text views along with  its ids.
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            nameTV = itemView.findViewById(R.id.idTVName);
        }
    }
}