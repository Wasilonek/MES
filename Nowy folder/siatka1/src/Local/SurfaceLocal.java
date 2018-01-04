package Local;

/**
 * Created by Kamil on 2017-12-31.
 */

public class SurfaceLocal {
    public final LocalNode[] localNode;
    public double shapeFunction[][];

    public SurfaceLocal(LocalNode localPoint1, LocalNode localPoint2) {
        localNode = new LocalNode[2];

        localNode[0] = localPoint1;
        localNode[1] = localPoint2;

        shapeFunction = new double[2][4]; // 2 punkty na powierzchni oraz 4 funkcje kształtu
    }
}
