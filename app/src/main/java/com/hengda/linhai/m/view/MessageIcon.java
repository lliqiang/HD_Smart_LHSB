package com.hengda.linhai.m.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MessageIcon extends ImageView {


	private Paint paint=null;
	private TextPaint textPaint=null;
	private int num=-1;
	public void setNum(int num){
		this.num=num;
		invalidate();
	}
	public int getNum(){
		return num;
	}
	private void init(){
		paint=new Paint();
		textPaint=new TextPaint();
		paint.setAntiAlias(true);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(18);
		paint.setColor(Color.RED);
		textPaint.setColor(Color.WHITE);
	}
	public MessageIcon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public MessageIcon(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public MessageIcon(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int cx=3;


			cx=(int) (getWidth()-getPaddingRight()-0.5f*textPaint.measureText(num+""));

		
		int cy=(getPaddingTop());
		int r=getPaddingTop()>getPaddingRight()?getPaddingTop():getPaddingRight();
		super.onDraw(canvas);

		if(num>0){
		canvas.drawCircle(cx, cy, r, paint);

		}
		
	}

}
