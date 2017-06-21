package playeranim.athou.com.playerpathanimator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final long DURATION = 800;
    private static final float SCALE_FACTOR = 12f;
    private static final int MIN_X_DISTANCE = 500;

    ImageButton mFab;
    ViewGroup mFabContainer;
    ViewGroup controlContainer;

    private boolean mRevealFlag = false;
    private int mFabSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = (ImageButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFabAnima();
            }
        });
        mFab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mFab.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mFab.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mFabSize = mFab.getHeight();
            }
        });
        mFabContainer = (ViewGroup) findViewById(R.id.container);
        controlContainer = (ViewGroup) findViewById(R.id.control_container);
    }

    float startX = 0;
    float startY = 0;

    private void startFabAnima() {
        startX = mFab.getX();
        startY = mFab.getY();
        AnimatorPath path = new AnimatorPath();
        path.moveTo(0, 0);
        path.cubicTo(-200, 300, -400, 150, -600, 100);
//        path.lineTo(0, 0);

        ObjectAnimator animator = ObjectAnimator.ofObject(this, "translation", new PlayTypeEvaluator(), path.getPoints().toArray());
        animator.setDuration(DURATION);
//        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Math.abs(startX - mFab.getX()) > MIN_X_DISTANCE) {
                    if (!mRevealFlag) {
                        mRevealFlag = true;

                        mFab.setBackgroundResource(R.drawable.shape_fab_expend); //替换背景
                        mFab.setImageDrawable(new BitmapDrawable()); //清除按钮图片
                        mFabContainer.setY(mFabContainer.getY() + mFabSize / 2); //修正按钮容器的Y坐标

                        //添加爆炸动画
                        mFab.animate()
                                .scaleX(SCALE_FACTOR)
                                .scaleY(SCALE_FACTOR)
                                .setDuration(400)
                                .setListener(animatorListener)
                                .start();
                    }
                }
            }
        });
    }

    public void setTranslation(PathPoint point) {
        mFab.setTranslationX(point.x);
        if (mRevealFlag) { //修正按钮的位置，此时按钮会偏下，所以需要向上调按钮的位置，即要把Y轴减小
            mFab.setTranslationY(point.y - mFabSize / 2);
        } else {
            mFab.setTranslationY(point.y);
        }
    }

    //爆炸动画监听
    private AnimatorListenerAdapter animatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //爆炸完成
            mFab.setVisibility(View.INVISIBLE); //隐藏掉按钮
            mFabContainer.setBackgroundColor(getResources().getColor(R.color.lightAccent)); //将容器背景色设置成爆炸色

            int count = controlContainer.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = controlContainer.getChildAt(i);
                //给每个子view添加缩放动画
                ViewPropertyAnimator animator = child.animate().scaleX(1).scaleY(1).setDuration(300);
                //依次展示出来
                animator.setStartDelay(i * 100);
                animator.start();
            }
        }
    };
}
