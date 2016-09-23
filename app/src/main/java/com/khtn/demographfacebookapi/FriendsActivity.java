package com.khtn.demographfacebookapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();



        if(accessToken!=null)
        {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                Log.d("ABC",object.getJSONObject("friends").toString());
                            } catch (JSONException e) {
                                Log.d("Lỗi",e.toString());
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "name,friends{name,picture}");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
}
