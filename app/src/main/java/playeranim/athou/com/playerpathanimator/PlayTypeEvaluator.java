package playeranim.athou.com.playerpathanimator;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2017/6/21.
 */
public class PlayTypeEvaluator implements TypeEvaluator<PathPoint> {

    @Override
    public PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue) {
        float x, y;
        if (endValue.opration == PathPoint.CUBIC) { //贝塞尔曲线，查看3阶贝塞尔曲线公式
            float fraction = 1 - t;
            x = startValue.x * fraction * fraction * fraction
                    + 3 * endValue.mControl0X * t * fraction * fraction
                    + 3 * endValue.mControl1X * t * t * fraction
                    + endValue.x * t * t * t;
            y = startValue.y * fraction * fraction * fraction
                    + 3 * endValue.mControl0Y * t * fraction * fraction
                    + 3 * endValue.mControl1Y * t * t * fraction
                    + endValue.y * t * t * t;
        } else {
            x = startValue.x + t * (endValue.x - startValue.x);
            y = startValue.y + t * (endValue.y - startValue.y);
        }
        return PathPoint.moveTo(x, y);
    }
}
