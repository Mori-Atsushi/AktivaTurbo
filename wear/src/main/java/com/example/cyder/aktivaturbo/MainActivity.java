package com.example.cyder.aktivaturbo;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    /** 再生ボタン */
    private ImageButton playButton;
    /** 再生ボタンの状態をあらわす。ture=再生、false=停止 */
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            /** この関数の内部でWatchViewStub内のViewの初期化*/
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
//                ViewのIDを検索
                playButton = (ImageButton)findViewById(R.id.play_button);
//                押されている状態か否かを設定
                playButton.setActivated(isPlaying);
//                押されたときの挙動
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isPlaying = !isPlaying;
                        v.setActivated(isPlaying);
                    }
                });
            }
        });
    }
}
