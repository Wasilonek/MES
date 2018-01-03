package ProgramMES;

import java.io.FileNotFoundException;

public class Element {
    public Node[] nodeId; // 4 wezly w elemencie
    public int[] globalNodeId;
    private Surface[] surfaceForOneElement;     // powierzchnia dla elementu
    private int theNumberOfSurfacesThatAreInContactWithTheSurroundings;
    private int[] localNumberOfSurfacesThatAreInContactWithTheSurroundings;
    GlobalData globalData = GlobalData.getInstance();


    public Element(int x, int y, Node[] nodeId) throws FileNotFoundException {

        this.nodeId = new Node[4];
        surfaceForOneElement = new Surface[4];
        globalNodeId = new int[4];

        // Nadaje wsporzedne wezłow w elemencie
        this.nodeId[0] = nodeId[0];
        this.nodeId[1] = nodeId[1];
        this.nodeId[2] = nodeId[2];
        this.nodeId[3] = nodeId[3];

        surfaceForOneElement[0] = new Surface(this.nodeId[3], this.nodeId[0]);
        surfaceForOneElement[1] = new Surface(this.nodeId[0], this.nodeId[1]);
        surfaceForOneElement[2] = new Surface(this.nodeId[1], this.nodeId[2]);
        surfaceForOneElement[3] = new Surface(this.nodeId[2], this.nodeId[3]);

        globalNodeId[0] = globalData.getnH() * x + y;
        globalNodeId[1] = globalData.getnH() * (x + 1) + y;
        globalNodeId[2] = globalData.getnH() * (x + 1) + (y + 1);
        globalNodeId[3] = globalData.getnH() * x + (y + 1);


        theNumberOfSurfacesThatAreInContactWithTheSurroundings = 0;
        for (int k = 0; k < 4; k++) {
            if (surfaceForOneElement[k].getPointsForOneSurface()[0].getStatus() == 1 && surfaceForOneElement[k].getPointsForOneSurface()[1].getStatus() == 1) {
                theNumberOfSurfacesThatAreInContactWithTheSurroundings++;
            }
        }

        localNumberOfSurfacesThatAreInContactWithTheSurroundings = new int[theNumberOfSurfacesThatAreInContactWithTheSurroundings];
        int counter = 0;
        for (int k = 0; k < 4; k++) {
            if (surfaceForOneElement[k].getPointsForOneSurface()[0].getStatus() == 1 && surfaceForOneElement[k].getPointsForOneSurface()[1].getStatus() == 1) {
                localNumberOfSurfacesThatAreInContactWithTheSurroundings[counter++] = k;

            }
        }
    }

    public int getTheNumberOfSurfacesThatAreInContactWithTheSurroundings() {
        return theNumberOfSurfacesThatAreInContactWithTheSurroundings;
    }

    public int[] getLocalNumberOfSurfacesThatAreInContactWithTheSurroundings() {
        return localNumberOfSurfacesThatAreInContactWithTheSurroundings;
    }

}
