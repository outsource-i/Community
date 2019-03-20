package com.onion.community.view;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public  class BasePopupWindow extends PopupWindow{


	View contentView;
	public BasePopupWindow(Context context) {
        super(context);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		//this.setAnimationStyle(R.style.AnimBottom);
		this.setBackgroundDrawable(null);
		
	}
	@Override
	public void setContentView(View contentView) {
		super.setContentView(contentView);
		this.contentView=contentView;
		if(contentView==null)
		return;
       contentView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
						dismiss();
					}
				return true;
			}
		});
       contentView.setFocusableInTouchMode(true); 
       contentView.setOnKeyListener(new OnKeyListener() {  
    	    @Override  
    	    public boolean onKey(View v, int keyCode, KeyEvent event) {  
    	        if (keyCode == KeyEvent.KEYCODE_BACK) {  
    	        	dismiss();
    	            return true;  
    	        }  
    	        return false;  
    	    }  
    	});  
	}
	
	
}
