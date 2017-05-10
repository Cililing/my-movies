package com.example.przemek.mymoviesv3.Other;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;


public class SingleLineTextView extends android.support.v7.widget.AppCompatTextView {

    public SingleLineTextView(Context context) {
        super(context);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public SingleLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public SingleLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs, defStyleAttr);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final Layout layout = getLayout();
        if (layout == null) {
            final int lineCount = layout.getLineCount();
            if (lineCount > 0) {
                final float textSize = getTextSize();

                //set text size
                setTextSize(TypedValue.COMPLEX_UNIT_PX, (textSize - 1));

                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
}
