package ProgramMES;


import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        GlobalData gd = GlobalData.getInstance();
        Grid grid = Grid.getInstance();
        double[] t;

        for (int itau = 0; itau < gd.getTau(); itau+= gd.getDtau()) {
            gd.computeMetrix();
            t = Gauss.gaussElimination(gd.getNh(), gd.getH_global(), gd.getP_global());
            for (int i = 0; i < gd.getNh(); i++) {
                grid.ND[i].setT(t[i]);
            }
        }

//        gd.showMatrixH();
//        System.out.println("\n\n");
//
//        gd.showVectorP();
//        System.out.println("\n\n");

        int count = 0;
        for (int i = 0; i < gd.getnB(); i++) {
            for (int j = 0; j < gd.getnH(); j++) {
                System.out.printf("%.15f\t", grid.ND[count++].getTemperature());
            }
            System.out.println("");
        }
        System.out.println("\n\n");

    }
}
