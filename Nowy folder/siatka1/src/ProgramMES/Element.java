package ProgramMES;

import java.io.FileNotFoundException;

/**
 * Created by Kamil on 2017-12-31.
 */
public class Element {

    public Node[] nodes; // 4 wezly w elemencie

    public int[] globalNode;

    private Surface[] surfaceForOneElement;

    // Liczba powierzchni które są w kontakcie z otoczeniem ( nałozony zostaje warunek brzegowy )
    private int theNumberOfSurfacesThatAreInContactWithTheSurroundings;

    // Lokalny numer konkretnej powierzchni która jest w kontakcie z otoczeniem
    private int[] localNumberOfSurfacesThatAreInContactWithTheSurroundings;

    GlobalData globalData = GlobalData.getInstance();


    public Element(int i, int j, Node[] nodes) throws FileNotFoundException {

        this.nodes = new Node[4];
        surfaceForOneElement = new Surface[4];
        globalNode = new int[4];

        // Nadawanie node z siatki do node elementu
        this.nodes[0] = nodes[0];
        this.nodes[1] = nodes[1];
        this.nodes[2] = nodes[2];
        this.nodes[3] = nodes[3];

        // Określanie wspórzędnych dla konkretnych powierzchni na podstawie  węzłów
        surfaceForOneElement[0] = new Surface(this.nodes[3], this.nodes[0]);
        surfaceForOneElement[1] = new Surface(this.nodes[0], this.nodes[1]);
        surfaceForOneElement[2] = new Surface(this.nodes[1], this.nodes[2]);
        surfaceForOneElement[3] = new Surface(this.nodes[2], this.nodes[3]);

        // Potrzebne do agregacji ( wzór do nadawanie kolejnych wspórzędnych w siatce , zgodnie z ruchem odwrotny do zegara )
        globalNode[0] = globalData.getnH() * i + j;
        globalNode[1] = globalData.getnH() * (i + 1) + j;
        globalNode[2] = globalData.getnH() * (i + 1) + (j + 1);
        globalNode[3] = globalData.getnH() * i + (j + 1);


        // Jeśli status punktów ktore składają sie na powierzchnie maja status 1 oznacza ze na powierzchnie bedzie nakładany warunek brzegowy
        theNumberOfSurfacesThatAreInContactWithTheSurroundings = 0;
        for (int k = 0; k < 4; k++) {
            if (surfaceForOneElement[k].getPointsForOneSurface()[0].getStatus() == 1 && surfaceForOneElement[k].getPointsForOneSurface()[1].getStatus() == 1) {
                theNumberOfSurfacesThatAreInContactWithTheSurroundings++;
            }
        }

        // Po obliczniu całkowitej ilości powierzchni alokujemy tablice i nadajemy odpowiednim powierzchnią lokalne numery
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
