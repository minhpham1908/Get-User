package com.minh.getdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button getBtn;
    ListView listView;
    ArrayList<User> listUser;
    UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listUser = new ArrayList<>();
        listView = findViewById(R.id.userList);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TAG", "Begin!");
                String link = ((EditText)findViewById(R.id.inputText)).getText().toString();
                new HTTPTask().execute(link);
            }
        });

    }

    private class HTTPTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "getting infomation", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... urls) {
            String link = urls[0];
            String inputLine;
            StringBuilder response = new StringBuilder();
            try {
                URL url = null;
                url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                Log.v("TAG", "Sending 'GET' request to URL : " + url.toString());
                Log.v("TAG", "Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            } catch (Exception e) {
                Log.v("TAG", String.valueOf(e));
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.v("TAG", result);
            int id;
            String name, email, address, phone, website, company;
            try {
                JSONArray users = new JSONArray(result);
                for (int i=0;i<users.length();i++) {
                    JSONObject user = users.getJSONObject(i);
                    id = user.getInt("id");
                    name = user.getString("name");
                    email = user.getString("email");
                    address = getAddress((JSONObject) user.get("address"));
                    website = user.getString("website");
                    company =((JSONObject) user.get("company")).getString("name");
                    phone = user.getString("phone");
                    listUser.add(new User(id,name, email, address, phone,website, company));
                    userListAdapter = new UserListAdapter(listUser);
                    listView.setAdapter(userListAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private String getAddress(JSONObject address) throws JSONException {
        String result, street, suite, city;
        street =  address.getString("street");
        suite =  address.getString("suite");
        city =  address.getString("city");
        StringBuilder sb =new StringBuilder();
        sb.append(suite);
        sb.append(", ");
        sb.append(street);
        sb.append(", ");
        sb.append(city);
        result = sb.toString();
        return result;
    }
}


