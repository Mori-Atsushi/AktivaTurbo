package com.example.cyder.aktivaturbo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kousuke nezu on 2017/03/02.
 */

public class CircleGauge extends View {
	/** 文章 */
	private String text;
	/** textの文字色(デフォルトは白) */
	private int textColor = Color.WHITE;
	/** ゲージ自体の背景色 */
	private int gaugeBackgroundColor;
	/** ゲージ自体の前景色 */
	private int gaugeForegroundColor;
	/** ゲージの半径(ゲージの一番外側の円を半径とします) */
	private int gaugeRadius;
	/** ゲージの太さ */
	private int gaugeWidth;

	public CircleGauge(Context context) {
		super(context);
	}

	public CircleGauge(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CircleGauge(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
}
