package com.khtn.demographfacebookapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookDialog;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

public class PostStatusActivity extends AppCompatActivity {

    EditText txtStatus;
    Button btnConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_status);

        txtStatus = (EditText) findViewById(R.id.txtStatus);
        btnConfirm = (Button) findViewById(R.id.btnConfirmPost);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostStatus();
            }
        });
    }

    private void PostStatus()
    {
        if(txtStatus.getText().equals(""))
        {
            return;
        }
        Bundle params = new Bundle();
        params.putString("message", txtStatus.getText().toString());
                /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Toast.makeText(PostStatusActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        txtStatus.setText("");
                    }
                }
        ).executeAsync();
    }
}
