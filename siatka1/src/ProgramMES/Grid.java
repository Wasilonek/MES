package ProgramMES;

import java.io.FileNotFoundException;

public class Grid {

    Node ND[];
    Element EL[];
    GlobalData globalData;



    private Grid() throws FileNotFoundException {
        globalData = GlobalData.getInstance();
        ND = new Node[globalData.getNh()];
        EL = new Element[globalData.getNe()];

        //Tworzenie wezłów
        int k = 0;
        for (int i = 0; i < globalData.getnB(); i++) {
            for (int j = 0; j < globalData.getnH(); j++) {
                ND[k++] = new Node(i * (globalData.getB() / (globalData.getnB() - 1)), j * (globalData.getH() / (globalData.getnH() - 1)));
            }
        }

        //Tworzenie elementów
        k = 0;
        for (int i = 0; i < globalData.getnB() - 1; i++) {
            for (int j = 0; j < globalData.getnH() - 1; j++) {
                EL[k++] = new Element(i, j, new Node[]{ND[globalData.getnH() * i + j], ND[globalData.getnH() * (i + 1) + j], ND[globalData.getnH() * (i + 1) + (j + 1)], ND[globalData.getnH() * i + (j + 1)]});
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
            System.out.println("i:" + i + "\tStatus:" + ND[i].getStatus() + "\t(" + ND[i].getX() + ";" + ND[i].getY() + ")");
        }
    }

    public void showElements(int i) {
        System.out.println("ELEMENT:" + i);
        for (int j = 0; j < 4; j++) {
            System.out.println("Global ID:" + EL[i].globalNodeId[j] + "\t(" + EL[i].nodeId[j].getX() + ";" + EL[i].nodeId[j].getY() + ")" + "\tStatus:" + EL[i].nodeId[j].getStatus() );
        }
    }

    public Node[] getND() {
        return ND;
    }

    public Element[] getEL() {
        return EL;
    }

    public GlobalData getGlobalData() {
        return globalData;
    }

    public static Grid getGrid() {
        return grid;
    }
}
