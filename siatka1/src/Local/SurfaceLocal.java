package Local;

/**
 * Created by Kamil on 2017-12-31.
 */
public class SurfaceLocal {
    public final NodeLocal[] nodeLocals;

    public final double N[][];

    public SurfaceLocal(NodeLocal node1, NodeLocal node2) {
        nodeLocals = new NodeLocal[2];

        nodeLocals[0] = node1;
        nodeLocals[1] = node2;

        N = new double[2][4];
    }
}
