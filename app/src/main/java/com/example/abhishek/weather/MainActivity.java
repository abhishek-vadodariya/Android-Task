package com.example.abhishek.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import GetterSetter.Pojo;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity
{

    private Activity activity;
    private List<Pojo> pojolist = new ArrayList<>();
    private RecyclerView recycler_view;
    private  RecyclerAdapter myAdapter;
    private RequestQueue rq;
    private String TAG ="";
    public String url ="https://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=97868fa41cb0577026c598535dbf790d";
    private ProgressBar loader;
    private Toolbar my_toolbar;
    private ImageView img_toolbar;

    //https://samples.openweathermap.org/data/2.5/forecast?appid=97868fa41cb0577026c598535dbf790d
    //97868fa41cb0577026c598535dbf790d

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;

        recycler_view= (RecyclerView)findViewById(R.id.recycler_view);
        loader = (ProgressBar)findViewById(R.id.loader);
        my_toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        //setSupportActionBar(my_toolbar);
        img_toolbar = (ImageView)findViewById(R.id.img_toolbar);
        loader.setVisibility(VISIBLE);



        myAdapter = new RecyclerAdapter(pojolist);
        recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter( myAdapter );


        FindWeather();
        OnClick();
    }

    private void FindWeather()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                for(int i = 0; i < response.length(); i++)
                {
                    try {
                        Pojo pojo = new Pojo();

                        JSONObject obj = new JSONObject(response.toString());
                        // Log.d("JSON OBJ", "onResponse: "+ obj.get("cod"));


                        JSONArray jsonArray = obj.getJSONArray("list");
                        //Log.d("JSON ARRAY", "onResponse: "+ jsonArray.get(0));
                        JSONObject mainobj = jsonArray.getJSONObject(i);
                        //Log.d("mainValue", "onResponse: "+mainobj.toString());
                        //Log.d("mainlist", "onResponse: "+mainobj.get("main").toString());
                        JSONObject mainList = mainobj.getJSONObject("main");


                        /*pojo.setTxt_temp(mainList.get("temp").toString());
                        pojo.setTxt_pressure(mainList.get("pressure").toString());
                        pojo.setTxt_humidity(mainList.get("humidity").toString());
                        pojo.setTxt_min_temp(mainList.get("temp_min").toString());
                        pojo.setTxt_mex_temp(mainList.get("temp_max").toString());*/

                       // String temp = String.valueOf(mainList.getDouble("temp"));
                        double temp_int = Double.parseDouble(mainList.get("temp").toString());
                        double centi = ((temp_int * 9) / (5+32));
                        centi = Math.round(centi);
                        int t = (int) centi;
                        pojo.setTxt_temp(String.valueOf(t)+" C");

                        String pressure = String.valueOf("Pressure : " + mainList.getDouble("pressure")+"mBar");
                        pojo.setTxt_pressure(pressure);

                        double humidity = Double.parseDouble(mainList.get("humidity").toString());
                        double hum = (humidity);
                        hum = Math.round(hum * 100)/(100);
                        int h = (int) hum;
                        pojo.setTxt_humidity("Humidity : "+String.valueOf(h)+"%");

                        double min_temp=Double.parseDouble(mainList.get("temp_min").toString());
                        double cent = ((temp_int * 9) / (5+32));
                        cent = Math.round(cent);
                        int c = (int) cent;
                        pojo.setTxt_min_temp("Min Temp : " +String.valueOf(c).toString()+" C");

                        double max_temp = Double.parseDouble(mainList.get("temp_max").toString());
                        double cen = ((temp_int * 9) / (5+32));
                        cen = Math.round(cent);
                        int a = (int) cen;
                        pojo.setTxt_mex_temp("Max Temp : "+String.valueOf(a).toString()+" C");
                        pojolist.add(pojo);
                        myAdapter.notifyDataSetChanged();
                        loader.setVisibility(GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                //Log.d("TestLog", "FindWeather: "  );

                //myAdapter = new RecyclerAdapter(pojolist);
                //recycler_view.setAdapter(myAdapter);
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.v(TAG,"Volley Error: " ,error);
            }
        });

        rq = Volley.newRequestQueue(this);
             rq.add(jsonObjectRequest);


    }
    private void OnClick()
    {
        img_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MY_PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent= new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

                /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);*/
            }
        });
    }


}
