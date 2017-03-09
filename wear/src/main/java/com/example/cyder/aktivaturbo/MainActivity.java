package com.example.cyder.aktivaturbo;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	/** レイアウト */
	private RelativeLayout relativeLayout;
    /** 再生ボタン */
    private ImageButton playButton;
	/** 速度ゲージ */
	private CircleGauge circleGauge;
    /** 再生ボタンの状態をあらわす。ture=再生、false=停止 */
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rect_activity_main);
//		RelativeLayoutの初期化
		relativeLayout = (RelativeLayout)findViewById(R.id.rect_relativelayout);
//		ViewのIDを検索
		playButton = (ImageButton)findViewById(R.id.play_button);
//		押されている状態か否かを設定
		playButton.setActivated(isPlaying);
//      押されたときの挙動
		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isPlaying = !isPlaying;
				v.setActivated(isPlaying);
			}
		});
		circleGauge = (CircleGauge)findViewById(R.id.speed_circlegauge);
    }

    @Override
    protected void onResume(){
		super.onResume();
	}
}
