package jl2119.nurbs;

import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NFunction {
    private final double MAX =  10000f;
    private final double MIN = -10000f;

    //Map<Long, List<Double>> termMap;
    Map<Long, Double> knots;
    long i;
    long degreeN;

    public NFunction(long i, long degreeN, Map<Long, Double> knots) {
        //termMap = new HashMap<>();
        this.i = i;
        this.degreeN = degreeN;
        this.knots = knots;
    }

    public double Apply(double u) {
        // Base case
        if (degreeN <= 1) {
            if (u > knots.get(i) && u <= knots.get(i + 1))
                return 1;
            return 0;
        }

        double n1 = new NFunction(i, degreeN - 1, knots).Apply(u);
        double n2 = new NFunction(i + 1, degreeN - 1, knots).Apply(u);
        double f = fFunction(u, i, degreeN);
        double g = gFunction(u, i, degreeN);

        return n1 * f + n2 * g;
    }

    private double fFunction(double u, long i, long n) {
        double val = (u - knots.get(i)) / (knots.get(i + n - 1) - knots.get(i));
        return val;
    }

    private double gFunction(double u, long i, long n) {
        double val = (knots.get(i + n) - u)/(knots.get(i + n) - knots.get(i + 1));
        return val;
    }
}
