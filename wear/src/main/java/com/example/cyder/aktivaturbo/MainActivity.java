package com.example.cyder.aktivaturbo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener, View.OnTouchListener {

    /** レイアウト */
    private RelativeLayout relativeLayout;
    /** 再生ボタン */
    private ImageButton playButton;
    /** 速度ゲージ */
    private CircleGauge speedGauge;
    /** セクションゲージ */
    private CircleGauge sectionGauge;
    /** プレイングゲージ */
    private CircleGauge playingGauge;
    /** 頭出しボタン */
    private ImageButton cueButton;
    /** 早戻しボタン */
    private ImageButton reverseButton;
    /** 再生ボタンの状態をあらわす。ture=再生、false=停止 */
    private boolean isPlaying = false;
    /** 巻き戻しボタンの状態をあらわす。ture=巻き戻し中、false=通常 */
    private boolean isReverse = false;

    /** mobileとの通信のためのクラス */
    MobileCommunicate mobileCommunicate;

    /** 一定時間ごとに処理を行うタイマー */
    private Timer timer;

    /** 定数 */
    private final float maxDgree = 240;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        四角形のレイアウトを読み込み。将来的には変更必要
        setContentView(R.layout.rect_activity_main);
        //Viewを初期化
        findViews();
        Log.d("MainActivity", "onCreate通過");
//        通信用クラスのインスタンス化
        mobileCommunicate = new MobileCommunicate();
        mobileCommunicate.connect(this);

        timer = new Timer();
        TimerTask timerTask = new MyTimerTask(this);
        timer.scheduleAtFixedRate(timerTask, 0, 30);
    }

    /**
     * Viewを初期化するためのメソッド
     */
    private void findViews(){
        //        ViewのIDを検索
        playButton = (ImageButton)findViewById(R.id.play_button);
        //        押されている状態か否かを設定
        playButton.setActivated(isPlaying);
//      押されたときの挙動
        playButton.setOnClickListener(this);

        speedGauge = (CircleGauge)findViewById(R.id.speed_circlegauge);
        speedGauge.setDegree((float)(360 * 0.8));
        sectionGauge = (CircleGauge)findViewById(R.id.section_circlegauge);
        sectionGauge.setDegree(maxDgree);
        playingGauge = (CircleGauge)findViewById(R.id.playing_circlegauge);

        cueButton = (ImageButton)findViewById(R.id.cue_button);
        cueButton.setOnClickListener(this);
        reverseButton = (ImageButton)findViewById(R.id.reverse_button);
        reverseButton.setOnTouchListener(this);
    }

    float nowPlayDegree = 0;
    public void InvalidateScreen(){
        if(isReverse) {
            nowPlayDegree -= 0.4;
        } else {
            if(!isPlaying)
                nowPlayDegree += 0.2;
        }
        if(nowPlayDegree > maxDgree || nowPlayDegree < 0)
            nowPlayDegree = 0;

        playingGauge.setDegree(nowPlayDegree);
    }

    int i = 0;
    /**
     * Viewをクリックしたときに発生するリスナーを受け取るメソッド
     * @param v クリックされたView
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                isPlaying = !isPlaying;
                v.setActivated(isPlaying);
                //使用例
                mobileCommunicate.syncData(
                        MobileCommunicate.AKTIVA_SEND_PATH, //パスを指定(おそらくこのままで問題ないはず)
                        MobileCommunicate.KEY[0],             //キー　変更したいもののキーを入力
                        String.valueOf(i++));                 //バリュー　変更する値を入力
                Log.d("MainActivity", String.valueOf(i));
                break;
            case R.id.cue_button:
                nowPlayDegree = 0;
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.reverse_button:
                int action = event.getAction();
                switch(action) {
                    case MotionEvent.ACTION_DOWN:
                        isReverse = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isReverse = false;
                        break;
                }
                break;
        }
        return false;
    }
}
