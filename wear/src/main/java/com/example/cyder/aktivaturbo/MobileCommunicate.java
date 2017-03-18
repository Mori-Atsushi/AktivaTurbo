package com.example.cyder.aktivaturbo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * ！！このクラスはスマホ側と完全に統一してください！！
 * Created by kousuke nezu on 2017/03/14.
 */

public class MobileCommunicate extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, ResultCallback<DataApi.DataItemResult> {
    //    ログ用のタグ
    private static String TAG = "MobileCommunicate";


    /** データを保存するようのDataMapです */
    private DataMap data;

    /**
     * データ送信用パス
     * このパスにデータは保存される。
     */
    public static final String AKTIVA_SEND_PATH = "/com/cyder/activa/data";

    //以下データ
    public static final int KEYNUM = 3;   // キーの数

    public static final String[] KEY = {    // キーの名前
            "is_playing",           // 再生状況
            "speed",                 // 速度
            "now_play_time"};       // 現在の再生時間
    private static int[] VALUE = {    // キーのdata(絶対に上のKEYの順番と合わせてください！！！)
            0,
            0,
            0};

    /** Google Play Serviceインスタンス */
    GoogleApiClient mGoogleApiClient;


    /**
     * コンストラクタ
     * 初期化メソッドを呼ぶのみ
     */
    public MobileCommunicate(){
        init();
    }

    /**
     * 一番最初の元データを作る
     */
    public void init(){
        data = new DataMap();
        for(int i = 0; i < KEYNUM; i++) {
            //!必ず文字列で挿入してください！
            data.putString(KEY[i], String.valueOf(VALUE[i]));
        }
    }

    /**
     * 通信を接続する
     * @param context コンテキスト
     */
    public void connect(Context context){
        //        mGoogleApiClientのインスタンス化
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addApi(Wearable.API)
                .build();

        //        Google Play Serviceに接続
        //        アプリが立ち上がっているかどうかにかかわらず同期させたいのでonCreate()で接続
        mGoogleApiClient.connect();
        //データ変更を受け取れるようにする
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    /**
     * Google Play Serviceに接続成功したとき呼び出される
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "接続成功");
    }

    /**
     * Google Play Serviceにサスペンドしたときに呼び出される
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "サスペンド");
    }

    public void disConnect(){
        Log.d(TAG, "接続を切断しました");
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Wearにデータを送信
     * Google Play Serviceにデータを保管し共有します。
     * @param path パス名
     * @param key キー
     * @param value バリュー
     */
    public void syncData(String path, String key, String value){
        Log.d(TAG, "データを送信します.");

        // DataMapインスタンスを生成する
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create(path);
        DataMap dataMap = dataMapRequest.getDataMap();

        // データをセットする
//        dataMap.putString(key, value);
        dataMap.putAll(data);
        dataMap.putString(key, value);

        // データを更新する
        PutDataRequest request = dataMapRequest.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, request);
        pendingResult.setResultCallback(this);
    }

    /**
     * 同期結果を受け取るメソッド
     */
    @Override
    public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
        Log.d("TAG", "onResult: " + dataItemResult.getStatus());
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents){
        for(DataEvent event: dataEvents){
            //            データアイテムが削除されたとき
            if(event.getType() == DataEvent.TYPE_DELETED){
                Log.d(TAG, "データアイテムが削除されました。");
            }
            //            データアイテムが変更されたとき
            else if(event.getType() == DataEvent.TYPE_CHANGED){
                Log.d(TAG, "データアイテムが変更されました。");

                //パス名が同じ場合は値を取得
                if(AKTIVA_SEND_PATH.equals(event.getDataItem().getUri().getPath())){
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                    for(int i = 0; i < KEYNUM; i++) {
                        String dataItem = dataMapItem.getDataMap().getString(KEY[i]);
                        if(!data.getString(KEY[i]).equals(dataItem) && dataItem != null) {
                            //！必ず文字列で取り出してください！
                            Log.d(TAG, String.valueOf(dataItem));
                            data.putString(KEY[i], dataItem);
                        }
                    }
                }
            }
        }
    }
}
