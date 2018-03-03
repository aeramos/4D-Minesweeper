public class Vector {
    double x, y, z, length;

    public Vector(double x, double y, double z) {
        length = Math.sqrt(x * x + y * y + z * z);
        if (length > 0) {
            this.x = x / length;
            this.y = y / length;
            this.z = z / length;
        }
    }

    public Vector cross(Vector v) {
        Vector crossed = new Vector(y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x);
        return crossed;
    }
}
