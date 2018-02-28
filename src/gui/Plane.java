package gui;

public class Plane {
    Vector v1, v2, nV;
    double[] p = new double[3];

    public Plane(DDDgon dp) {
        p[0] = dp.x[0];
        p[1] = dp.y[0];
        p[2] = dp.z[0];

        v1 = new Vector(dp.x[1] - dp.x[0],
                dp.y[1] - dp.y[0],
                dp.z[1] - dp.z[0]);

        v2 = new Vector(dp.x[2] - dp.x[0],
                dp.y[2] - dp.y[0],
                dp.z[2] - dp.z[0]);

        nV = v1.cross(v2);
    }

    public Plane(Vector vec1, Vector vec2, double[] z) {

        p = z;
        v1 = vec1;
        v2 = vec2;
        nV = v1.cross(v2);
    }
}
