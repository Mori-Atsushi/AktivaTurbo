package com.example.cyder.aktivaturbo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    MobileCommunicate mobileCommunicate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        通信用クラスのインスタンス化
        mobileCommunicate = new MobileCommunicate();
        mobileCommunicate.connect(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
