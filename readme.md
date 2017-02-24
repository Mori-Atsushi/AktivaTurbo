# Aktiva Turbo

これはAktivaを操作するためのAndroid Wearアプリです。

## 環境構築
このプロジェクトの開発のためには、以下のものをインストールする必要があります。

* [android studio](https://developer.android.com/studio/index.html?hl=ja)

## デバッグ
このプロジェクトのデバッグには、Androidスマートフォン及びAndroid Wear、もしくはそれらのエミュレータが必要となります。

### Androidスマートフォン及びAndroid Wearによるデバッグ
スマートフォン及びWearにて、「Bluetooth経由のデバッグ」を設定し、以下のadbコマンドによって接続してください。

```
adb forward tcp:4444 localabstract:/adb-hub
adb connect 127.0.0.1:4444
```

## チーム

* @Mori-Atsushi
* @Kousuke-N
* @Nishihara-Daiki