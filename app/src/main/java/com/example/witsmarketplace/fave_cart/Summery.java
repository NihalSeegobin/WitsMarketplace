package com.example.witsmarketplace.fave_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.witsmarketplace.LandingPage.LandingPage;
import com.example.witsmarketplace.Login.ServerCommunicator;
import com.example.witsmarketplace.R;
import com.example.witsmarketplace.SharedPreference;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Summery extends AppCompatActivity {

    ImageButton backbut;
    TextView changeMethod;
    TextView location;
    TextView itemNum;
    TextView Delivery;
    Button complete;
    String Address;
    TextView price;
    private RequestQueue requestQueue;
    String email;
    String webURL = "https://lamp.ms.wits.ac.za/home/s2172765/cart_items.php?ID=";
    ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    public static SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summery);

        requestQueue = Volley.newRequestQueue(this);
       sharedPreference = new SharedPreference(this);
        renderItems(sharedPreference.getSH("email"));
        email = sharedPreference.getSH("email");

        backbut = findViewById(R.id.sumback);
        complete = findViewById(R.id.complete);
        changeMethod = findViewById(R.id.textView9);
        location = findViewById(R.id.textView11);
        itemNum = findViewById(R.id.textView3);
        Delivery = findViewById(R.id.textView8);
        price = findViewById(R.id.textView6);
        getDataFromServer(email);

        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Summery.this, LandingPage.class);
                startActivity(intent);
            }
        });

        changeMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = "";
                String Price = "";
                String control;
                String pictures = "";

                for(int i = 0; i< cartItems.size();i++){
                    control = cartItems.get(i).getPrice().replace("R","").replace(" ","");
                    Price = Price + control + ",";
                    names = names + cartItems.get(i).getName();
                    pictures = pictures + cartItems.get(i).getImage();
                }

                JSONObject order = new JSONObject();
                try {
                    order.put("NAME", names);
                    order.put("PICTURE", pictures);
                    order.put("PRICE", Price);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                String string_order = "[" + gson.toJson(order) + "]";
                Toast.makeText(Summery.this, string_order,Toast.LENGTH_LONG).show();
                //SaveOrder(email, string_order, Address);
            }
        });

    }

    private void AsignValues() {

        itemNum.setText(String.valueOf(cartItems.size()) + "  Items");
        String pricestr;
        int Price = 0;
        for(int i = 0; i< cartItems.size();i++){
            pricestr = cartItems.get(i).getPrice().replace("R","").replace(" ","");
            Price += Integer.parseInt(pricestr);
        }
        price.setText("R "+String.valueOf(Price));

        String temp = sharedPreference.getSH("Address");
        String[] temp2 = temp.split(",");
        location.setText(temp);

        JSONObject order = new JSONObject();
        try {
            order.put("Street", temp2[0]);
            order.put("Surburb", temp2[1]);
            order.put("City", temp2[2]);
            order.put("Country", temp2[3]);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Address = gson.toJson(order);
        Toast.makeText(Summery.this,Address,Toast.LENGTH_LONG).show();
    }

    private void parseData(JSONArray array) throws JSONException {

        Log.d("Cart Items", String.valueOf(array.getJSONObject(0)));

        String name = "", price = "", image = "";
        Log.d("Size", String.valueOf(array.length()));
        for (int i = 0; i < array.length(); i++) {

            JSONObject json = null;

            try {
                json = array.getJSONObject(i);

                //Adding data to the request object
                name = json.getString("NAME");
                price = json.getString("PRICE");
                image = json.getString("PICTURE");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String[] imageURLs = image.split(",");
            String image_url = imageURLs[0];

            cartItems.add(new CartItem(name, price, image_url));
        }
        AsignValues();
    }

    private JsonArrayRequest getDataFromServer(String email) {

        return new JsonArrayRequest(webURL + String.valueOf(email),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            parseData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //If an error occurs that means end of the list has reached
                    }
                });
    }

    private void getData(String email) {

        requestQueue.add(getDataFromServer(email));
    }

    private void renderItems(String email) {

        getData(email);
    }

    public void SaveOrder(String email, String name, String address){
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("ORDER_NAME", name);
        contentValues.put("ADDRESS", address);

        new ServerCommunicator("https://lamp.ms.wits.ac.za/home/s2172765/app_insert_into_order.php", contentValues) {
            @Override
            protected void onPreExecute() {}


            @Override
            protected void onPostExecute(String output) {
                try {
                    System.out.println(output);
                    JSONArray users = new JSONArray(output);
                    JSONObject object = users.getJSONObject(0);

                    String status = object.getString("add_status");
                    String message = object.getString("status_message");
                    System.out.println(price);
                    if(status.equals("1")){
                        Intent intent = new Intent(Summery.this, LandingPage.class);
                        startActivity(intent);
                        Toast.makeText(Summery.this ,"Order Completed Successfully",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Summery.this, message , Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

}