import java.awt.*;

public class DDDgon {
    Color c;
    Cube associatedCube;
    double[] x, y, z;
    boolean draw = true, transparent;
    double[] calcPos, nX, nY;
    PolygonObject drawable;
    double avgDist;

    public DDDgon(double[] xSet, double[] ySet, double[] zSet, Color color, boolean t, Cube associated) {
        transparent = t;
        c = color;
        x = xSet;
        y = ySet;
        z = zSet;
        associatedCube = associated;
        createPolygon();
    }

    public void createPolygon() {
        drawable = new PolygonObject(new double[x.length], new double[x.length], c, Screen.dddgons.size(), transparent, associatedCube);
    }

    public void updatePolygon() {
        nX = new double[x.length];
        nY = new double[x.length];
        draw = true;
        for (int i = 0; i < x.length; i++) {
            calcPos = Calculator.calculatePositionP(Screen.viewFrom, Screen.viewTo, x[i], y[i], z[i]);
            nX[i] = (HyperSweeper.screenSize.getWidth() / 2 - Calculator.calcFocusPos[0]) + calcPos[0] * Screen.zoom;
            nY[i] = (HyperSweeper.screenSize.getHeight() / 2 - Calculator.calcFocusPos[1]) + calcPos[1] * Screen.zoom;
            if (Calculator.t < 0) {
                draw = false;
            }
        }

        calcLighting();

        drawable.draw = draw;
        drawable.updatePolygon(nX, nY);
        avgDist = getDist();
    }

    public void calcLighting() {
        Plane lightingPlane = new Plane(this);
        double angle = Math.acos(((lightingPlane.nV.x * Screen.light[0]) +
                (lightingPlane.nV.y * Screen.light[1]) + (lightingPlane.nV.z * Screen.light[2]))
                / (Math.sqrt(Screen.light[0] * Screen.light[0] + Screen.light[1] * Screen.light[1] + Screen.light[2] * Screen.light[2])));

        drawable.lighting = 0.2 + 1 - Math.sqrt(Math.toDegrees(angle) / 180);

        if (drawable.lighting > 1) {
            drawable.lighting = 1;
        }
        if (drawable.lighting < 0) {
            drawable.lighting = 0;
        }
    }

    public double getDist() {
        double total = 0;
        for (int i = 0; i < x.length; i++) {
            total += getDistanceToP(i);
        }
        return total / x.length;
    }

    public double getDistanceToP(int i) {
        return Math.sqrt((Screen.viewFrom[0] - x[i]) * (Screen.viewFrom[0] - x[i]) +
                (Screen.viewFrom[1] - y[i]) * (Screen.viewFrom[1] - y[i]) +
                (Screen.viewFrom[2] - z[i]) * (Screen.viewFrom[2] - z[i]));
    }
}
