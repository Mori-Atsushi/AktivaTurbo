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

	/** デフォルト(0%)の角度 */
	private final float defaultDegree = -90;
	/** ゲージの角度 */
	private float degree = 120;

	private float centerX;
	private float centerY;

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

		centerX = getWidth() / 2;
		centerY = getHeight() / 2;
	}

	/**
	 * ゲージを描画します。
	 * 順序は
	 * 1.ゲージの外側の円を描画
	 * 2.ゲージ部分の外側の扇形の描画
	 * 3.ゲージ部分の内側の扇形の描画
	 * 4.ゲージの内側の円を描画
	 * @param canvas 描画するキャンバス
	 */
	@Override
	public void onDraw(Canvas canvas){
		Paint paint = new Paint();

//		ゲージの後ろ部分を描画
		paint.setColor(gaugeBackgroundColor);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, gaugeRadius, paint);


//		ゲージの前部分を描画
		paint.setColor(gaugeForegroundColor);
		RectF rect = new RectF(getWidth() / 2 - 150, getWidth() / 2 - 150, getWidth() / 2 + 150, getWidth() / 2 + 150);
		canvas.drawArc(rect, defaultDegree, degree, true, paint);

		paint.setColor(mainBackgroundColor);
		RectF rect2 = new RectF(getWidth() / 2 - 150 + gaugeWidth, getWidth() / 2 - 150 + gaugeWidth, getWidth() / 2 + 150 - gaugeWidth, getWidth() / 2 + 150 - gaugeWidth);
		canvas.drawArc(rect2, defaultDegree, degree, true, paint);

		paint.setColor(mainBackgroundColor);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, gaugeRadius - gaugeWidth, paint);
	}
	}
}
