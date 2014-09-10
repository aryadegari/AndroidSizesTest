package com.notionplex.sizetest;

import com.vanteon.calculator.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

public class MyTextView extends TextView {
	
	boolean bold=false;
	
	public MyTextView(Context context) {
		super(context);
		setTypeFace(context);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		

		if (bold) {
			Typeface custom_font = Typeface.createFromAsset(
					context.getAssets(), "fonts/Roboto-Medium.ttf");
			this.setTypeface(custom_font);
		} else {
			setTypeFace(context);
		}

	}

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTypeFace(context);
	}

	public void setTypeFace(Context context) {
		Typeface custom_font = Typeface.createFromAsset(context.getAssets(),
				"fonts/Roboto-Thin.ttf");
		this.setTypeface(custom_font);
	}

	public boolean isMyTextViewBoldText() {
		return bold;
	}

	public void setMyTextViewBoldText(boolean boldText) {
		bold = boldText;
		if (bold) {
			Typeface custom_font = Typeface.createFromAsset(
					getContext().getAssets(), "fonts/Roboto-Medium.ttf");
			this.setTypeface(custom_font);
		} else {
			setTypeFace(getContext());
		}
	}
}
