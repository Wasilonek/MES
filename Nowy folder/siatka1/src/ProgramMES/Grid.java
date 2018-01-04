package ProgramMES;

import java.io.FileNotFoundException;

/**
 * Created by Kamil on 2017-12-31.
 */
public class Grid {

    Node nodes[];
    Element elements[];

    GlobalData globalData = GlobalData.getInstance();

    private Grid() throws FileNotFoundException {

        nodes = new Node[globalData.getNh()];
        elements = new Element[globalData.getNe()];

        //Tworzenie wezłów , nH	to liczba wezlow po wysokosci , nB to liczba wezlow po szerokości
        int count = 0;
        for (int i = 0; i < globalData.getnB(); i++) {
            for (int j = 0; j < globalData.getnH(); j++) {
                nodes[count++] = new Node(i * (globalData.getB() / (globalData.getnB() - 1)), j * (globalData.getH() / (globalData.getnH() - 1)));
            }
        }

        //Tworzenie elementów, nH to liczba wezlow po wysokosci , nB to liczba wezlow po szerokości
        count = 0;
        for (int i = 0; i < globalData.getnB() - 1; i++) {
            for (int j = 0; j < globalData.getnH() - 1; j++) {
                elements[count++] = new Element(i, j, new Node[]{nodes[globalData.getnH() * i + j], nodes[globalData.getnH() * (i + 1) + j], nodes[globalData.getnH() * (i + 1) + (j + 1)], nodes[globalData.getnH() * i + (j + 1)]});
            }
        }
    }


    private static Grid grid = null;
    public static Grid getInstance() throws FileNotFoundException {
        if (grid == null) {
            grid = new Grid();
        }
        return grid;
    }

    public void showNodes() {
        for (int i = 0; i < globalData.getNh(); i++) {
            System.out.println("i:" + i + "\tStatus:" + nodes[i].getStatus() + "\t(" + nodes[i].getX() + ";" + nodes[i].getY() + ")");
        }
    }

    public void showElements(int i) {
        System.out.println("ELEMENT:" + i);
        for (int j = 0; j < 4; j++) {
            System.out.println("Global ID:" + elements[i].globalNode[j] + "\t(" + elements[i].nodes[j].getX() + ";" + elements[i].nodes[j].getY() + ")" + "\tStatus:" + elements[i].nodes[j].getStatus() );
        }
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Element[] getElements() {
        return elements;
    }

    public GlobalData getGlobalData() {
        return globalData;
    }

    public static Grid getGrid() {
        return grid;
    }
}
