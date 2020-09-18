package jl2119.nurbs;

import jl2119.Vector.Vector;

import java.io.IOException;

public class Plot {
    private final Renderer renderer;

    private final double minX, maxX;
    private final double minZ, maxZ;
    private final double lineSpacing;
    private final Map2D<Long, Long, Vector> pointGrid;
    private final long gridSize;
    private final NurbSurfaceGenerator nurb;
    private final double EPSILON = 0.05f;

    public Plot(double xRange, double zRange, double lineSpacing,
                Map2D<Long, Long, Vector> pointGrid, long gridSize) {
        this.minX = 1.0;
        this.maxX = xRange;
        this.minZ = 1.0;
        this.maxZ = zRange;
        this.lineSpacing = lineSpacing;
        this.gridSize = gridSize;
        this.nurb = new NurbSurfaceGenerator(pointGrid, 4, gridSize);
        this.pointGrid = pointGrid;

        Vector camera = new Vector(6, 5, -5.5);
        renderer = new Renderer(500,500, "./assets/nurbs.png", camera);
    }

    // Draws the wire-frame with specified line spacing
    public void Draw() throws IOException {
        // Plot all x-lines
        double x = minX + lineSpacing;
        while (x < maxX) {
            PlotXLine(x);
            System.out.println("Printing x:" + x);
            x += lineSpacing;
        }
        // Plot all z-line
        double z = minZ + lineSpacing;
        while (z < maxZ) {
            PlotZLine(z);
            System.out.println("Printing z:" + z);
            z += lineSpacing;
        }

        renderer.GenerateImage();
    }

    private void PlotXLine(double x) {
        Vector point;
        double y;
        double z = minZ;

        while (z < maxZ) {
            y = GetY(x, z);
            point = new Vector(x, y, z);
            renderer.RenderPoint(point);
            z += EPSILON;
        }
    }

    private void PlotZLine(double z) {
        Vector point;
        double y;
        double x = minX;

        while (x < maxX) {
            y = GetY(x, z);
            point = new Vector(x, y, z);
            renderer.RenderPoint(point);
            x += EPSILON;
        }
    }

    protected double GetY(double x, double z) {
        return nurb.Apply(x, z);
    }
}
