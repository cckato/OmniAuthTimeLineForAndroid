package com.cocoabu.omniauthtimelineforandroid.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cocoabu.omniauthtimelineforandroid.R;
import com.cocoabu.omniauthtimelineforandroid.util.AppMacro;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button twBtn = (Button)findViewById(R.id.twBtn);
        final Button fbBtn = (Button)findViewById(R.id.fbBtn);

        twBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(AppMacro.TAG, "Twitterで登録するよー");
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(AppMacro.TAG, "Facebookで登録するよー");
            }
        });


    }

}
