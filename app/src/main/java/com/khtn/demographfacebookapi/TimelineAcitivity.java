package com.khtn.demographfacebookapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;

public class TimelineAcitivity extends AppCompatActivity {

    ListView lvpost;
    ArrayList<MyPost> listMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_acitivity);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        lvpost = (ListView) findViewById(R.id.lvPosts);
        listMP = new ArrayList<>();

        if(accessToken!=null)
        {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            showListPost(getListPost(object));
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "posts{story,created_time,id,picture,message}");
            request.setParameters(parameters);
            request.executeAsync();
        }

        lvpost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                postComment(position);
                return true;
            }
        });
    }

    private void postComment(int position) {

        Bundle params = new Bundle();
        params.putString("message", "This is a test comment");
                /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+listMP.get(position).getId()+"/comments",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Toast.makeText(TimelineAcitivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ).executeAsync();
    }


    private void showListPost(ArrayList<MyPost> myPosts)
    {
        listMP = myPosts;
        ArrayAdapter<MyPost> adapter =
                new ArrayAdapter<MyPost>(TimelineAcitivity.this,android.R.layout.simple_list_item_1,listMP);
        lvpost.setAdapter(adapter);
    }

    private ArrayList<MyPost> getListPost(JSONObject object)
    {
        ArrayList<MyPost> myPosts = new ArrayList<>();

        try {
            JSONArray jsonArray = object.getJSONObject("posts").getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                MyPost mp = new MyPost();
                mp.setId(jsonArray.getJSONObject(i).get("id").toString());
                mp.setCreated_time(jsonArray.getJSONObject(i).get("created_time").toString());
                try {
                    mp.setStory(jsonArray.getJSONObject(i).get("story").toString());
                }
                catch (JSONException ex)
                {
                    mp.setStory("");
                }
                try {
                    mp.setPicture(jsonArray.getJSONObject(i).get("picture").toString());
                }
                catch (JSONException ex)
                {
                    mp.setPicture("");
                }
                try {
                    mp.setMessage(jsonArray.getJSONObject(i).get("message").toString());
                }
                catch (JSONException ex)
                {
                    mp.setMessage("");
                }

                myPosts.add(mp);
            }

        } catch (JSONException e) {
           Log.d("error",e.toString());
        }

        return myPosts;
    }
}
