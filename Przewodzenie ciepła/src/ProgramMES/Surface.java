package ProgramMES;

/**
 * Created by Kamil on 2017-12-07.
 */
public class Surface {
    private Node[] pointsForOneSurface;

    public Surface(Node point1 , Node point2) {

        pointsForOneSurface = new Node[2];
        this.pointsForOneSurface[0] = point1;
        this.pointsForOneSurface[1] = point2;
    }

    public Node[] getPointsForOneSurface() {
        return pointsForOneSurface;
    }
}
