public class Calculator {
    public static double t = 0;
    public static Vector w1, w2, viewVector, rotationVector, directionVector, planeVector1, planeVector2;
    public static Plane p;
    public static double[] calcFocusPos = new double[2];

    public static double[] calculatePositionP(double[] viewFrom, double[] viewTo, double x, double y, double z) {
        double[] projP = getProj(viewFrom, viewTo, x, y, z, p);
        double[] drawP = getDrawP(projP[0], projP[1], projP[2]);
        return drawP;
    }

    public static double[] getDrawP(double x, double y, double z) {
        double drawX = w2.x * x + w2.y * y + w2.z * z;
        double drawY = w1.x * x + w1.y * y + w1.z * z;
        return new double[]{drawX, drawY};
    }

    public static double[] getProj(double[] viewFrom, double[] viewTo, double x, double y, double z, Plane plane) {
        Vector ViewToPoint = new Vector(x - viewFrom[0], y - viewFrom[1], z - viewFrom[2]);

        t = (plane.nV.x * plane.p[0] + plane.nV.y * plane.p[1] + plane.nV.z * plane.p[2]
                - (plane.nV.x * viewFrom[0] + plane.nV.y * viewFrom[1] + plane.nV.z * viewFrom[2]))
                / (plane.nV.x * ViewToPoint.x + plane.nV.y * ViewToPoint.y + plane.nV.z * ViewToPoint.z);

        x = viewFrom[0] + ViewToPoint.x * t;
        y = viewFrom[1] + ViewToPoint.y * t;
        z = viewFrom[2] + ViewToPoint.z * t;

        return new double[]{x, y, z};
    }

    public static Vector getRotationVector(double[] viewFrom, double[] viewTo) {
        double dx = Math.abs(viewFrom[0] - viewTo[0]);
        double dy = Math.abs(viewFrom[1] - viewTo[1]);
        double xRot, yRot;

        xRot = dy / (dx + dy);
        yRot = dx / (dx + dy);

        if (viewFrom[1] > viewTo[1]) {
            xRot = -xRot;
        }
        if (viewFrom[0] < viewTo[0]) {
            yRot = -yRot;
        }

        Vector v = new Vector(xRot, yRot, 0);
        return v;
    }

    public static void setPredeterminedInfo() {
        viewVector = new Vector(Screen.viewTo[0] - Screen.viewFrom[0], Screen.viewTo[1] - Screen.viewFrom[1], Screen.viewTo[2] - Screen.viewFrom[2]);
        directionVector = new Vector(1, 1, 1);
        planeVector1 = viewVector.cross(directionVector);
        planeVector2 = viewVector.cross(planeVector1);
        p = new Plane(planeVector1, planeVector2, Screen.viewTo);

        rotationVector = Calculator.getRotationVector(Screen.viewFrom, Screen.viewTo);
        w1 = viewVector.cross(rotationVector);
        w2 = viewVector.cross(w1);

        calcFocusPos = Calculator.calculatePositionP(Screen.viewFrom, Screen.viewTo, Screen.viewTo[0], Screen.viewTo[1], Screen.viewTo[2]);
        calcFocusPos[0] = Screen.zoom * calcFocusPos[0];
        calcFocusPos[1] = Screen.zoom * calcFocusPos[1];
    }

    public static int avgArr(int[] a){
        int sum = 0;
        for(int i : a)
            sum += i;
        return sum / a.length;
    }
}
