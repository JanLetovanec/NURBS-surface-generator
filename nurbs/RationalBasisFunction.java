package jl2119.nurbs;

import javax.management.relation.Relation;
import java.util.HashMap;
import java.util.Map;

public class RationalBasisFunction {
    private Map<Long, Double> xKnots;
    private Map<Long, Double> zKnots;
    private long i, j, degreeN;

    public RationalBasisFunction(long i, long j, long degreeN,
                                 Map<Long, Double> xKnots,
                                 Map<Long, Double> zKnots) {
        this.xKnots = xKnots;
        this.zKnots = zKnots;
        this.i = i;
        this.j = j;
        this.degreeN = degreeN;
    }

    public double Apply(double u, double v) {
        double nX = new NFunction(i, degreeN, xKnots).Apply(u);
        double nZ = new NFunction(j, degreeN, zKnots).Apply(v);
        return nX * nZ;
//        double bot = 0;
//        for (long p = 1; p <= degreeN; p++) {
//            for (long q = 1; q <= degreeN; q++) {
//                nX = new NFunction(p, degreeN, xKnots).Apply(u);
//                nZ = new NFunction(q, degreeN, zKnots).Apply(v);
//                bot += nX * nZ;
//            }
//        }
//        if (bot < 0.001 && bot > -0.001)
//            return 0;
//
//        return top / bot;
    }
}
