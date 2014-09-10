//-----------------------------------------------------------------------------
/*
	 Calculator UI Scaling Example
	 Copyright (C) 2012 Vanteon Corporation

	Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
	associated documentation files (the "Software"), to deal in the Software without restriction, including 
	without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
	copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to 
	the following conditions:

	The above copyright notice and this permission notice shall be included in all copies or substantial 
	portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN 
	NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
	WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
	SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.	 
*/
//-----------------------------------------------------------------------------

package com.notionplex.sizetest;

import java.util.Calendar;

import com.vanteon.calculator.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private boolean scalingComplete = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);
    }
    
    @Override
	public void onWindowFocusChanged(boolean hasFocus) {
    	
    	Log.i("sizes", "start...");
    	long starttime = Calendar.getInstance().getTimeInMillis();
		if (!scalingComplete) // only do this once
		{
	        scaleContents(findViewById(R.id.contents), findViewById(R.id.container));
	        scalingComplete = true;
		}
		long endtime = Calendar.getInstance().getTimeInMillis();
		Log.i("Sizes", "end..."+(endtime-starttime)+" ms");
		
		
	}

    /** Called when the views have been created. We override this in order to scale
     *  the UI, which we can't do before this. 
     */
    @Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View view = super.onCreateView(name, context, attrs);
		return view;
	}

	/** Scales the contents of the given view so that it completely fills the given 
	 * container on one axis (that is, we're scaling isotropically).
	 * @param rootView	The view that contains the interface elements
	 * @param container	The view into which the interface will be scaled
	 */
	private void scaleContents(View rootView, View container)
	{
	 	// Compute the scaling ratio. Note that there are all kinds of games you could
		// play here - you could, for example, allow the aspect ratio to be distorted
		// by a certain percentage, or you could scale to fill the *larger* dimension
		// of the container view (useful if, for example, the container view can scroll).
		int widthSizePx = rootView.getWidth();
		int heightSizePx = rootView.getHeight();
		
	 	float xScale = (float)container.getWidth() / widthSizePx;
	 	float yScale = (float)container.getHeight() / heightSizePx;
	 	
	 	Log.d("Sizes", "Device Sizes: width="+container.getWidth()+",height="+container.getHeight());
	 	Log.d("Sizes", "Content Sizes: width="+rootView.getWidth()+",height="+rootView.getHeight());
	 	Log.d("Sizes", "xScacle="+xScale+",yScale="+yScale);
	 	float scale = Math.min(xScale, yScale);
//	 	float scale = (xScale+yScale)/2.0f;
	 	
	 	// Scale our contents
	 	scaleViewAndChildren(rootView, scale,xScale,yScale);
	}

	/** Scale the given view, its contents, and all of its children by the given factor.
	 * @param root	The root view of the UI subtree to be scaled
	 * @param scale	The scaling factor
	 */
	public static void scaleViewAndChildren(View root, float scale , float xScale , float yScale)
	{
	 	// Retrieve the view's layout information
	 	ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
	
	 	// Scale the view itself
	 	if (layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT && 
	 		layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT)
	 	{
	 		layoutParams.width *= xScale;
	 	}
	 	if (layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT && 
	 		layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT)
	 	{
	 		layoutParams.height *= yScale;
	 	}
	 	
	 	// If this view has margins, scale those too
	 	if (layoutParams instanceof ViewGroup.MarginLayoutParams)
	 	{
	 		ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams)layoutParams;
	 		marginParams.leftMargin *= xScale;
	 		marginParams.rightMargin *= xScale;
	 		marginParams.topMargin *= yScale;
	 		marginParams.bottomMargin *= yScale;
	 	}
	 	
	 	// Set the layout information back into the view
	 	root.setLayoutParams(layoutParams);
	
	 	// Scale the view's padding
	 	root.setPadding(
	 			(int)(root.getPaddingLeft() * scale), 
	 			(int)(root.getPaddingTop() * scale), 
	 			(int)(root.getPaddingRight() * scale), 
	 			(int)(root.getPaddingBottom() * scale));
	 	
	 	// If the root view is a TextView, scale the size of its text. Note that this is not quite precise -
	 	// it appears that text can't be exactly scaled to any desired size, presumably due to limitations
	 	// of the font system. You may have to make your fonts a little bit smaller than you otherwise might
	 	// in order to make sure that the text will always fit at any scaling factor.
	 	if (root instanceof TextView)
	 	{
	 		TextView textView = (TextView)root; 
	 		Log.d("Sizes", "Scaling text size from " + textView.getTextSize() + " to " + textView.getTextSize() * scale);
	 		textView.setTextSize(textView.getTextSize() * scale);
	 	}
	 	
	 	// If the root view is a ViewGroup, scale all of its children recursively
	 	if (root instanceof ViewGroup)
	 	{
	 		ViewGroup groupView = (ViewGroup)root;
	 		for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
	 			scaleViewAndChildren(groupView.getChildAt(cnt), scale,xScale,yScale);
	 	}
	}    
}