package playeranim.athou.com.playerpathanimator;

/**
 * Created by Administrator on 2017/6/21.
 */

public class PathPoint {

    public static int MOVE = 0;
    public static int LINE = 1;
    public static int CUBIC = 2;

    int opration;
    float x, y;
    float mControl0X, mControl0Y;
    float mControl1X, mControl1Y;

    private PathPoint(int opration, float x, float y) {
        this.opration = opration;
        this.x = x;
        this.y = y;
    }

    private PathPoint(int opration, float mControl0X, float mControl0Y, float mControl1X, float mControl1Y, float x, float y) {
        this.opration = opration;
        this.mControl0X = mControl0X;
        this.mControl0Y = mControl0Y;
        this.mControl1X = mControl1X;
        this.mControl1Y = mControl1Y;
        this.x = x;
        this.y = y;
    }

    public static PathPoint moveTo(float x, float y) {
        return new PathPoint(MOVE, x, y);
    }

    public static PathPoint lineTo(float x, float y) {
        return new PathPoint(LINE, x, y);
    }

    public static PathPoint cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        return new PathPoint(CUBIC, x1, y1, x2, y2, x3, y3);
    }
}
