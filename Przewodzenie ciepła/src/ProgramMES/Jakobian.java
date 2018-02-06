package ProgramMES;

import Local.LocalElement;

import java.io.FileNotFoundException;

/**
 * Created by Kamil on 2017-12-31.
 */
public class Jakobian {

    LocalElement localElement = LocalElement.getInstance();

    double[][] Jakobian;

    // Wyznacznik jakobianu
    private double JakobianDet;

    // Jakobian odwrotny
    private double[][] JakobianOdw;

    // Punkt całkowania
    int integrationPoint;


    public Jakobian(int integrationPoint, double x[], double y[]) throws FileNotFoundException {

        this.integrationPoint = integrationPoint;
        Jakobian = new double[2][2];
        JakobianOdw = new double[2][2];

        JakobianDet = 0;

        // Wyzanczenie jakobianu przekształcenia
        Jakobian[0][0] = localElement.getdN_po_ksi()[integrationPoint][0] * x[0] + localElement.getdN_po_ksi()[integrationPoint][1] * x[1] + localElement.getdN_po_ksi()[integrationPoint][2] * x[2] + localElement.getdN_po_ksi()[integrationPoint][3] * x[3];
        Jakobian[1][0] = localElement.getdN_po_ksi()[integrationPoint][0] * y[0] + localElement.getdN_po_ksi()[integrationPoint][1] * y[1] + localElement.getdN_po_ksi()[integrationPoint][2] * y[2] + localElement.getdN_po_ksi()[integrationPoint][3] * y[3];
        Jakobian[0][1] = localElement.getdN_po_eta()[integrationPoint][0] * x[0] + localElement.getdN_po_eta()[integrationPoint][1] * x[1] + localElement.getdN_po_eta()[integrationPoint][2] * x[2] + localElement.getdN_po_eta()[integrationPoint][3] * x[3];
        Jakobian[1][1] = localElement.getdN_po_eta()[integrationPoint][0] * y[0] + localElement.getdN_po_eta()[integrationPoint][1] * y[1] + localElement.getdN_po_eta()[integrationPoint][2] * y[2] + localElement.getdN_po_eta()[integrationPoint][3] * y[3];


        JakobianDet = Jakobian[0][0] * Jakobian[1][1] - Jakobian[0][1] * Jakobian[1][0];


        JakobianOdw[0][0] = Jakobian[1][1];
        JakobianOdw[1][0] = -1 * Jakobian[1][0];
        JakobianOdw[0][1] = -1 * Jakobian[0][1];
        JakobianOdw[1][1] = Jakobian[0][0];
    }

    public void showJakobian() {
        System.out.println("Jakobian w punkcie calkowania:" + integrationPoint);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(Jakobian[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("Wyznacznik: " + JakobianDet);
    }

    public LocalElement getLocalElement() {
        return localElement;
    }

    public double[][] getJakobian() {
        return Jakobian;
    }

    public double getJakobianDet() {
        return JakobianDet;
    }

    public double[][] getJakobianOdw() {
        return JakobianOdw;
    }

    public int getIntegrationPoint() {
        return integrationPoint;
    }
}



