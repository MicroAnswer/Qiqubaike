package answer.android.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于测试没有用的
 * Created by Micro on 2017-10-22.
 */

public class NoUseViewGroup extends ViewGroup {

    private StringBuilder tt = new StringBuilder();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public NoUseViewGroup(Context context) {
        super(context);
        init(context,null);
    }

    public NoUseViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NoUseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        tt.append("onLayout\n");

        // 获取到子view
        View child = getChildAt(0);

        int ml = getPaddingLeft();
        int mt = getPaddingTop();
        int mr = r - getPaddingRight();
        int mb = mt + child.getMeasuredHeight();

        // 设定子View的绘制范围
        child.layout(ml,mt,mr,mb);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        View child = getChildAt(0);
        child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
        tt.append("onMeasure\n");

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            widthSize = getResources().getDisplayMetrics().widthPixels;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            // 不知道高度，我天，，使用子控件的高度
            heightSize = child.getMeasuredHeight();
        }

        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(tt.toString(), 100, 100,  paint);
    }
}
