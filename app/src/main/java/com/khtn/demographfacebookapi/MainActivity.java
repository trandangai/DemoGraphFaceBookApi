package com.khtn.demographfacebookapi;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView txtName;
    CallbackManager callbackManager;
    Button btnFriendUI,btntimeLineButton,btnPostStatus;
    LoginButton btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);


        // Tạo biến callback để kiểm soát quá trình đăng nhập
        callbackManager = CallbackManager.Factory.create();

        txtName = (TextView) findViewById(R.id.txtHello);

        btnLogin = (LoginButton) findViewById(R.id.login_button);


        btnLogin.setReadPermissions("user_friends","user_posts");

        // Biến nhận biết sự thay đổi của AcccessToken
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    txtName.setText("Please login first !");
                } else {
                    sayHello(currentAccessToken);
                }
            }
        };

        btnFriendUI = (Button) findViewById(R.id.btnFriendList);
        btntimeLineButton = (Button) findViewById(R.id.btnTimeLine);
        btnPostStatus = (Button) findViewById(R.id.btnPostStatus);
        addEvents();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void sayHello(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            int friends = object.getJSONObject("friends").getJSONObject("summary").getInt("total_count");
                            txtName.setText("Welcome " + object.getString("name") + " ! You have " + friends + " friends");
                            Log.d("json",object.toString());
                        } catch (JSONException e) {
                            txtName.setText(e.toString());
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,friends");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void addEvents()
    {

        if(AccessToken.getCurrentAccessToken()!=null)
        {
            sayHello(AccessToken.getCurrentAccessToken());
        }

        btnFriendUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FriendsActivity.class);
                startActivity(intent);
            }
        });

        btntimeLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TimelineAcitivity.class);
                startActivity(intent);
            }
        });

        btnPostStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PostStatusActivity.class);
                startActivity(intent);
            }
        });
    }
}
