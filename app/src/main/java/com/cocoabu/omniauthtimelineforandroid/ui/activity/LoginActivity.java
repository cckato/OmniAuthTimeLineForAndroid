package com.cocoabu.omniauthtimelineforandroid.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cocoabu.omniauthtimelineforandroid.R;
import com.cocoabu.omniauthtimelineforandroid.util.AppMacro;
import com.cocoabu.omniauthtimelineforandroid.util.TwitterUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class LoginActivity extends ActionBarActivity {

    public static final String EXTRA_LONGIN = "com.cocoabu.omniauthtimelineforandroid.ui.activity.EXTRA_LOGIN";

    private String callbackUrl;
    private Twitter twitter;
    private RequestToken requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackUrl = getString(R.string.twitter_callback_url);
        twitter = TwitterUtils.getTwitterInstance(this);

        final Button twBtn = (Button) findViewById(R.id.twBtn);
        final Button fbBtn = (Button) findViewById(R.id.fbBtn);

        //ボタンの定義
        twBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAuthorize();
            }
        });
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(AppMacro.TAG, "Facebookで登録するよー");
            }
        });
    }


    /**
     * OAuth認可を開始
     */
    private void startAuthorize() {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    requestToken = twitter.getOAuthRequestToken(callbackUrl);
                    return requestToken.getAuthorizationURL();
                } catch (TwitterException e) {
                    Log.e(AppMacro.TAG, "Twitterライブラリ実行時(1)にエラーが発生したよ");
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(String url) {
                if (url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    Log.e(AppMacro.TAG, "Twitter認証できなかったよ");
                }
            }
        };
        task.execute();
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intent == null
                || intent.getData() == null
                || !intent.getData().toString().startsWith(callbackUrl)) {
            return;
        }
        String verifier = intent.getData().getQueryParameter("oauth_verifier");

        AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
            @Override
            protected AccessToken doInBackground(String... params) {
                try {
                    return twitter.getOAuthAccessToken(requestToken, params[0]);
                } catch (TwitterException e) {
                    Log.e(AppMacro.TAG, "Twitterライブラリ実行時(2)にエラーが発生したよ");
                    e.printStackTrace();
                }
                return null;
            }
            @Override
        protected void onPostExecute(AccessToken accessToken) {
                if (accessToken != null) {
                    Toast.makeText(LoginActivity.this, "認証成功！", Toast.LENGTH_SHORT).show();
                    successOAuth(accessToken);
                } else {
                    Toast.makeText(LoginActivity.this, "認証失敗…", Toast.LENGTH_SHORT).show();
                }
            }
        };
        task.execute(verifier);
    }

    private void successOAuth(AccessToken accessToken) {
        TwitterUtils.storeAccessToken(this, accessToken);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_LONGIN, true);
        startActivity(intent);
        finish();
    }

}









