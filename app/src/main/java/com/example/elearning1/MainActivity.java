package com.example.elearning1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gtappdevelopers.cryptotracker.CurrencyModel;
import com.gtappdevelopers.cryptotracker.CurrencyRVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    private ArrayList<CurrencyModel> currencyModalArrayList;
    private CurrencyRVAdapter currencyRVAdapter;
    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText searchEdt = findViewById(R.id.idEdtCurrency);
        // initializing all our variables and array list.
        loadingPB = findViewById(R.id.idPBLoading);
        // creating variable for recycler view,
        // adapter, array list, progress bar
        RecyclerView currencyRV = findViewById(R.id.idRVcurrency);
        currencyModalArrayList = new ArrayList<>();
        // initializing our adapter class.
        currencyRVAdapter = new CurrencyRVAdapter(currencyModalArrayList, this);
        // setting layout manager to recycler view.
        currencyRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view.
        currencyRV.setAdapter(currencyRVAdapter);
        // calling get data method to get data from API.
        getData();
        // on below line we are adding text watcher for our
        // edit text to check the data entered in edittext.
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                // on below line calling a
                // method to filter our array list
                filter(s.toString());
            }
        });
    }
    private void filter(String filter) {
        // on below line we are creating a new array list
        // for storing our filtered data.
        ArrayList<CurrencyModel> filtrated = new ArrayList<>();
        // running a for loop to search the data from our array list.
        for (CurrencyModel item : currencyModalArrayList) {
            // on below line we are getting the item which are
            // filtered and adding it to filtered list.
            if (item.getName().toLowerCase().contains(filter.toLowerCase())) {
                filtrated.add(item);
            }
        }
        // on below line we are checking
        // weather the list is empty or not.
        if (filtrated.isEmpty()) {
            // if list is empty we are displaying a toast message.
            Toast.makeText(this, "No currency found..", Toast.LENGTH_SHORT).show();
        } else {
            // on below line we are calling a filter
            // list method to filter our list.
            currencyRVAdapter.filterList(filtrated);
        }
    }
    private void getData() {
        // creating a variable for storing our string.
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        // creating a variable for request queue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // making a json object request to fetch data from API.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            // inside on response method extracting data
            // from response and passing it to array list
            // on below line we are making our progress
            // bar visibility to gone.
            loadingPB.setVisibility(View.GONE);
            try {
                // extracting data from json.
                JSONArray dataArray = response.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObj = dataArray.getJSONObject(i);
                    String symbol = dataObj.getString("symbol");
                    String name = dataObj.getString("name");
                    JSONObject quote = dataObj.getJSONObject("quote");
                    JSONObject USD = quote.getJSONObject("USD");
                    double price = USD.getDouble("price");
                    // adding all data to our array list.
                    currencyModalArrayList.add(new CurrencyModel(name, symbol, price));
                }
                // notifying adapter on data change.
                currencyRVAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                // handling json exception.
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            // displaying error response when received any error.
            Toast.makeText(MainActivity.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                // in this method passing headers as
                // key along with value as API keys.
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "a38f67d5-b6e3-47ee-a39a-a9c9166f53a9");
                // at last returning headers
                return headers;
            }
        };
        // calling a method to add our
        // json object request to our queue.
        queue.add(jsonObjectRequest);
    }
}