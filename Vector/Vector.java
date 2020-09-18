package jl2119.Vector;

public class Vector {
    private final double EPSILON = 0.0001f;
    private double x, y, z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector Flatten(Vector normal) {
        double scalar = this.Scalar(normal.ToSize(1f));
        Vector component = normal.ScaleByScalar(scalar);

        return this.Sub(component);
    }

    public Vector ToSize(double targetLength) {
        double l = Length();
        Vector unit = new Vector(x/l,y/l,z/l);

        unit.x *= targetLength;
        unit.y *= targetLength;
        unit.z *= targetLength;

        return unit;
    }

    public Vector Sub(Vector v) {
        return this.Add(v.ScaleByScalar(-1f));
    }

    public Vector Add(Vector v) {
        return new Vector(x + v.x,y + v.y,z + v.z);
    }

    public Vector MirrorByNormal(Vector normal) {
        normal = normal.ToSize(1f);
        Vector flatVector = this.Flatten(normal);
        return this.Add(flatVector.ScaleByScalar(-2f));    // Subtract flat 2x
    }

    public double Length() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public double Scalar(Vector v) {
        return x * v.x
                + y * v.y
                + z * v.z;
    }

    public Vector ScaleByScalar(double scale) {
        return new Vector(x * scale, y * scale, z * scale);
    }

    public Vector GetComponentVector(Vector parallel){
        double length = parallel.ToSize(1.0).Scalar(this);
        return parallel.ToSize(length);
    }

    public Vector GetPerpendicularXUnit() {
        // Get random prependicular
        Vector result = new Vector(1,0,0);
        result = result.Flatten(this);
        if (result.Length() < EPSILON) {
            result = new Vector(0,1,0);
            result = result.Flatten(this);
        }
        // Stretch it to unit
        return result.ToSize(1f);
    }

    public Vector CrossedUnit(Vector v) {
        Vector result = new Vector(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
        return result.ToSize(1f);
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
