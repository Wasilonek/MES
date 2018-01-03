package ProgramMES;

import Local.ElementLocal;

import java.io.FileNotFoundException;

public class Jakobian {

    ElementLocal elementLocal = ElementLocal.getInstance();
    double[][] globalJakobian;
    private double globalJacobianDet;
    private double[][] globalJacobian_odw;
    int integrationPoint;


//    public Jakobian(int integrationPoint, Element globalElement) throws FileNotFoundException {
//
//        this.integrationPoint = integrationPoint;
//
//
//        globalJakobian = new double[2][2];
//        globalJacobian_odw = new double[2][2];
//
//        globalJacobianDet = 0;
//
//
//        globalJakobian[0][0] = elementLocal.getdN_po_ksi()[integrationPoint][0] * globalElement.nodeId[0].getX() + elementLocal.getdN_po_ksi()[integrationPoint][1] * globalElement.nodeId[1].getX() + elementLocal.getdN_po_ksi()[integrationPoint][2] * globalElement.nodeId[2].getX() + elementLocal.getdN_po_ksi()[integrationPoint][3] * globalElement.nodeId[3].getX();
//        globalJakobian[1][0] = elementLocal.getdN_po_ksi()[integrationPoint][0] * globalElement.nodeId[0].getY() + elementLocal.getdN_po_ksi()[integrationPoint][1] * globalElement.nodeId[1].getY() + elementLocal.getdN_po_ksi()[integrationPoint][2] * globalElement.nodeId[2].getY() + elementLocal.getdN_po_ksi()[integrationPoint][3] * globalElement.nodeId[3].getY();
//        globalJakobian[0][1] = elementLocal.getdN_po_eta()[integrationPoint][0] * globalElement.nodeId[0].getX() + elementLocal.getdN_po_eta()[integrationPoint][1] * globalElement.nodeId[1].getX() + elementLocal.getdN_po_eta()[integrationPoint][2] * globalElement.nodeId[2].getX() + elementLocal.getdN_po_eta()[integrationPoint][3] * globalElement.nodeId[3].getX();
//        globalJakobian[1][1] = elementLocal.getdN_po_eta()[integrationPoint][0] * globalElement.nodeId[0].getY() + elementLocal.getdN_po_eta()[integrationPoint][1] * globalElement.nodeId[1].getY() + elementLocal.getdN_po_eta()[integrationPoint][2] * globalElement.nodeId[2].getY() + elementLocal.getdN_po_eta()[integrationPoint][3] * globalElement.nodeId[3].getY();
//
//
//        globalJacobianDet = globalJakobian[0][0] * globalJakobian[1][1] - globalJakobian[0][1] * globalJakobian[1][0];
//
//
//        globalJacobian_odw[0][0] = globalJakobian[1][1];
//        globalJacobian_odw[1][0] = -1 * globalJakobian[1][0];
//        globalJacobian_odw[0][1] = -1 * globalJakobian[0][1];
//        globalJacobian_odw[1][1] = globalJakobian[0][0];
//
//    }

    //********************************************************************************************** Obliczanie jakobianu z lokalnego elementu

    public Jakobian(int integrationPoint, double x[], double y[]) throws FileNotFoundException {

        this.integrationPoint = integrationPoint;
        globalJakobian = new double[2][2];
        globalJacobian_odw = new double[2][2];

        globalJacobianDet = 0;


        globalJakobian[0][0] = elementLocal.getdN_po_ksi()[integrationPoint][0] * x[0] + elementLocal.getdN_po_ksi()[integrationPoint][1] * x[1] + elementLocal.getdN_po_ksi()[integrationPoint][2] * x[2] + elementLocal.getdN_po_ksi()[integrationPoint][3] * x[3];
        globalJakobian[1][0] = elementLocal.getdN_po_ksi()[integrationPoint][0] * y[0] + elementLocal.getdN_po_ksi()[integrationPoint][1] * y[1] + elementLocal.getdN_po_ksi()[integrationPoint][2] * y[2] + elementLocal.getdN_po_ksi()[integrationPoint][3] * y[3];
        globalJakobian[0][1] = elementLocal.getdN_po_eta()[integrationPoint][0] * x[0] + elementLocal.getdN_po_eta()[integrationPoint][1] * x[1] + elementLocal.getdN_po_eta()[integrationPoint][2] * x[2] + elementLocal.getdN_po_eta()[integrationPoint][3] * x[3];
        globalJakobian[1][1] = elementLocal.getdN_po_eta()[integrationPoint][0] * y[0] + elementLocal.getdN_po_eta()[integrationPoint][1] * y[1] + elementLocal.getdN_po_eta()[integrationPoint][2] * y[2] + elementLocal.getdN_po_eta()[integrationPoint][3] * y[3];


        globalJacobianDet = globalJakobian[0][0] * globalJakobian[1][1] - globalJakobian[0][1] * globalJakobian[1][0];


        globalJacobian_odw[0][0] = globalJakobian[1][1];
        globalJacobian_odw[1][0] = -1 * globalJakobian[1][0];
        globalJacobian_odw[0][1] = -1 * globalJakobian[0][1];
        globalJacobian_odw[1][1] = globalJakobian[0][0];
    }

    public void showGlobalJakobian() {
        System.out.println("Jakobian przy punkcie calkowania:" + integrationPoint);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(globalJakobian[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("Wyznacznik: " + globalJacobianDet);
    }

    public ElementLocal getElementLocal() {
        return elementLocal;
    }

    public double[][] getGlobalJakobian() {
        return globalJakobian;
    }

    public double getGlobalJacobianDet() {
        return globalJacobianDet;
    }

    public double[][] getGlobalJacobian_odw() {
        return globalJacobian_odw;
    }

    public int getIntegrationPoint() {
        return integrationPoint;
    }
}



