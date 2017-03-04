package com.example.cyder.aktivaturbo;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kousuke nezu on 2017/03/02.
 */

public class CircleGauge extends View {

	/** 背景 */
	int mainBackgroundColor;

	/** 文章 */
	private String text;
	/** textの文字色(デフォルトは白) */
	private int textColor = Color.WHITE;
	/** ゲージ自体の背景色 */
	private int gaugeBackgroundColor = Color.RED;
	/** ゲージ自体の前景色 */
	private int gaugeForegroundColor = Color.MAGENTA;
	/** ゲージの半径(ゲージの一番外側の円を半径とします) */
	private int gaugeRadius = 100;
	/** ゲージの太さ */
	private int gaugeWidth = 5;

	public CircleGauge(Context context) {
		super(context);
	}

	public CircleGauge(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CircleGauge(Context context, AttributeSet attrs) {
		super(context, attrs);

		Resources res = getResources();
		mainBackgroundColor = ContextCompat.getColor(context, R.color.main_background);

//		attrsファイルがヌルでないか
		if(attrs == null){
			return;
		}

//		カスタム属性を読み込む
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleGauge);
		gaugeRadius = array.getInteger(R.styleable.CircleGauge_gauge_radius, gaugeRadius);
		gaugeWidth = array.getInteger(R.styleable.CircleGauge_gauge_width, gaugeWidth);
		gaugeBackgroundColor = array.getColor(R.styleable.CircleGauge_gauge_background_color, gaugeBackgroundColor);
		gaugeForegroundColor = array.getColor(R.styleable.CircleGauge_gauge_foreground_color, gaugeForegroundColor);
		array.recycle();
	}

	public void onDraw(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(gaugeBackgroundColor);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, gaugeRadius, paint);
		paint.setColor(mainBackgroundColor);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, gaugeRadius - gaugeWidth, paint);
	}
}
