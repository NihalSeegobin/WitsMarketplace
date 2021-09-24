package com.example.witsmarketplace.LandingPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.witsmarketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

//    private RequestQueue requestQueue;
//    RecyclerView recyclerView;
//    Itembox_Adapter adapter;
//    ArrayList<ItemBox> search_results = new ArrayList<ItemBox>();
//    String searchURL = "https://lamp.ms.wits.ac.za/home/s2172765/Search.php?ID=";
//    Activity context = this;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        requestQueue = Volley.newRequestQueue(this);
//        getData();

        EditText search_bar = findViewById(R.id.txt_search);

        ImageView search_btn = findViewById(R.id.btn_search);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = search_bar.getText().toString();
                openSearchResults(key);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                System.out.println(search_results);
////                adapter = new Itembox_Adapter(search_results, context, 2);
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

    void openSearchResults(String search){
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra("search", search);

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("search_results", search_results);
//        intent.putExtra("search_bundle", bundle);
        startActivity(intent);
    }

//    CHANGES
//private void parseData(JSONArray array) {
//    String name="", price="", image="", description="";
//    for (int i = 0; i < array.length(); i++) {
//
//        //Creating the Request object
//        JSONObject json = null;
//
//        try {
//            json = array.getJSONObject(i);
//
//            //Adding data to the request object
//            name = json.getString("NAME");
//            price = json.getString("PRICE");
//            image = json.getString("PICTURE");
//            description = json.getString("DESCRIPTION");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //Adding the request object to the list
//        String[] imageURLs = image.split(",");
//        String image_url = imageURLs[0];
//
//        search_results.add(new ItemBox(name, "R " + price, image_url, description));
//    }
//    //Notifying the adapter that data has been added or changed
//    //adapter.notifyDataSetChanged();
//    renderer(search_results);
//}
//
//    private JsonArrayRequest getDataFromServer() {
//
//        //JsonArrayRequest of volley
//        return new JsonArrayRequest(searchURL,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        //Calling method to parse the json response
//                        parseData(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //If an error occurs that means end of the list has reached
//                    }
//                });
//    }
//
//    private void getData(){
//        requestQueue.add(getDataFromServer());
//
//    }
//
//    private void renderEverything(){
////        getData(search);
//    }
//
//    private void renderer(ArrayList<ItemBox> list){
//        //display the data in a recyclerview which allows us to scroll through
//        recyclerView = findViewById(R.id.rv_searchResults);
//        recyclerView.setHasFixedSize(true);
//        adapter = new Itembox_Adapter(list, this, 2);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.setAdapter(adapter);
//    }
}