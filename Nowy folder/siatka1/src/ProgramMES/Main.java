package ProgramMES;


import Solver.Gauss;

import java.io.FileNotFoundException;

/**
 * Created by Kamil on 2017-12-31.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        GlobalData globalData = GlobalData.getInstance();
        Grid grid = Grid.getInstance();
        ComputeMatrix computeMatrix = new ComputeMatrix();

        // do wyznaczenia
        double[] t;

        for (int tau = 0; tau < globalData.getTau(); tau += globalData.getDtau()) {

            computeMatrix.compute();


            // nh - wymiar macierzy (25)
            t = Gauss.gaussElimination(globalData.getNh(), computeMatrix.getGlobal_H(), computeMatrix.getGlobal_P());


            for (int i = 0; i < globalData.getNh(); i++) {
                grid.nodes[i].setT(t[i]);
            }

            double min, max;
            min = max = grid.nodes[0].getTemperature();

            for (int i = 1; i < globalData.getNh(); i++) {
                if (grid.nodes[i].getTemperature() > max)
                    max = grid.nodes[i].getTemperature();
                else if (grid.nodes[i].getTemperature() < min)
                    min = grid.nodes[i].getTemperature();
            }

            System.out.println();
            System.out.print("Tau = " + (tau + 50) + " \tMAX: ");
            System.out.printf("%.1f ", max);
            System.out.print("\tMIN: ");
            System.out.printf("%.1f ", min);
            System.out.println();


            int nodeNumber = 0;
            System.out.println("WARTOŚCI WĘZŁOWYCH TEMPERATUR:");
            for (int i = 0; i < globalData.getnB(); i++) {
                for (int j = 0; j < globalData.getnH(); j++) {
                    System.out.printf("%.1f", grid.nodes[nodeNumber++].getTemperature());
                    System.out.print("\t");
                }
                System.out.println("");
            }
        }
    }
}
