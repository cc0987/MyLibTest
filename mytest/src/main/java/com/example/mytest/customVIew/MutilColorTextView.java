package com.example.mytest.customVIew;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.mytest.R;

/**
 * Created by CC
 */

public class MutilColorTextView extends TextView {
    private Context mContext;

    public MutilColorTextView(Context context) {
        super(context);
        mContext = context;
    }

    public MutilColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MutilColorTextView);
        int color = array.getColor(R.styleable.MutilColorTextView_color, Color.BLACK);
        int size = array.getInt(R.styleable.MutilColorTextView_textSize, (int)getTextSize());
        String divideString = array.getString(R.styleable.MutilColorTextView_divideString);
        if (divideString!=null) {
            setDivideColor(color,size,divideString);
        }
    }

    public MutilColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setDivideColor(int color,String divideString){
        setDivideColor(color,(int)getTextSize(),divideString);
    }

    public void setDivideColor(int color,int size,String divideString) {
        String string = getText().toString();
        int i = string.indexOf(divideString);
        i=i<0?string.length():i+divideString.length();
        SpannableStringBuilder builder = new SpannableStringBuilder(getText().toString());
        ColorStateList theColor = ColorStateList.valueOf(color);
        TextAppearanceSpan redSpan = new TextAppearanceSpan(null, 0, size, theColor, null);
        builder.setSpan(redSpan, i, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        setText(builder);
    }
}
