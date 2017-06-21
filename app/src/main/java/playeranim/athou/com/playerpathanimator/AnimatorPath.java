package playeranim.athou.com.playerpathanimator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2017/6/21.
 */

public class AnimatorPath {
    ArrayList<PathPoint> points = new ArrayList<>();

    public void moveTo(float x, float y) {
        points.add(PathPoint.moveTo(x, y));
    }

    public void lineTo(float x, float y) {
        points.add(PathPoint.lineTo(x, y));
    }

    public void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        points.add(PathPoint.cubicTo(x1, y1, x2, y2, x3, y3));
    }

    public Collection<PathPoint> getPoints() {
        return points;
    }
}
