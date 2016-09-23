package com.khtn.demographfacebookapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

public class TimelineAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_acitivity);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();



        if(accessToken!=null)
        {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.d("post",object.toString());
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "posts{story,created_time,id,picture}");
            request.setParameters(parameters);
            request.executeAsync();

        }
    }
}
