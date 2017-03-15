package com.example.cyder.aktivaturbo;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import static java.lang.Math.*;

/**
 * Created by kousuke nezu on 2017/03/02.
 */

public class CircleGauge extends View {

	/** 背景 */
	private int mainBackgroundColor;

	/** 文章 */
	private String text = "sample";
	/** textのサイズ(デフォルトは25) */
	private int textSize = 25;
	/** textの文字色(デフォルトは白) */
	private int textColor = Color.WHITE;
	/** textのx座標の中心点に対する倍率(デフォルトは0.85) */
	private float textXposRate = 0.85f;
	/** ゲージ自体の背景色 */
	private int gaugeBackgroundColor = Color.RED;
	/** ゲージ自体の前景色 */
	private int gaugeForegroundColor = Color.MAGENTA;
	/** ゲージの半径(ゲージの一番外側の円を半径とします) */
	private int gaugeRadius = 100;
	/** ゲージの太さ */
	private int gaugeWidth = 5;

	/** デフォルト(0%)の角度 */
	private final float defaultDegree = -90;
	/** ゲージの角度 */
	private float degree = 0;

	private float centerX = getWidth() / 2;	//ゲージの中央のx座標
	private float centerY = getHeight() / 2;	//ゲージの中央のy座標

	public CircleGauge(Context context, AttributeSet attrs) {
		super(context, attrs);

		mainBackgroundColor = ContextCompat.getColor(context, R.color.main_background);
		textColor = ContextCompat.getColor(context, R.color.gauge_text_color);
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;

//		attrsファイルがヌルでないかチェック
		if(attrs == null){
			return;
		}

//		カスタム属性を読み込む
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleGauge);
		text = array.getString(R.styleable.CircleGauge_gauge_text);
		gaugeRadius = array.getInteger(R.styleable.CircleGauge_gauge_radius, gaugeRadius);
		gaugeWidth = array.getInteger(R.styleable.CircleGauge_gauge_width, gaugeWidth);
		gaugeBackgroundColor = array.getColor(R.styleable.CircleGauge_gauge_background_color, gaugeBackgroundColor);
		gaugeForegroundColor = array.getColor(R.styleable.CircleGauge_gauge_foreground_color, gaugeForegroundColor);
		array.recycle();
	}

	/**
	 * ゲージを描画します。
	 * 順序は
	 * 1.ゲージの外側の円を描画
	 * 2.ゲージ部分の外側の扇形の描画
	 * 3.ゲージの内側の円を描画
	 * 4.文字を描画
	 * @param canvas 描画するキャンバス
	 */
	@Override
	public void onDraw(Canvas canvas){
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;
		Paint paint = new Paint();
		paint.setAntiAlias(true);

//		(1)
		paint.setColor(gaugeBackgroundColor);
		canvas.drawCircle(centerX, centerY, gaugeRadius, paint);

//		(2)
		paint.setColor(gaugeForegroundColor);
		RectF rect = new RectF(centerX - gaugeRadius, centerX - gaugeRadius, centerX + gaugeRadius, centerX + gaugeRadius);
		canvas.drawArc(rect, defaultDegree, degree, true, paint);
		canvas.drawCircle(centerX, centerY - gaugeRadius + gaugeWidth / 2, gaugeWidth / 2, paint);
		canvas.drawCircle(centerX + (float)sin(toRadians(degree)) * (gaugeRadius - gaugeWidth / 2),
				centerY - (float)cos(toRadians(degree)) * (gaugeRadius - gaugeWidth / 2),
				gaugeWidth / 2, paint);

//		(3)
		paint.setColor(mainBackgroundColor);
		canvas.drawCircle(centerX, centerY, gaugeRadius - gaugeWidth, paint);

//		(4)
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.RIGHT);
		canvas.drawText(
				text,
				(float) (centerX * textXposRate),
				centerY - gaugeRadius + gaugeWidth / 2 - (paint.getFontMetrics().ascent + paint.getFontMetrics().descent) / 2,
				paint);
	}

//	角度の値が360度を超えないよう設定
	public void setDegree(float degree){
		this.degree = degree % 360;
		invalidate();
	}
}
