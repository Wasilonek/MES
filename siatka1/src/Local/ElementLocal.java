package Local;

import ProgramMES.Node;
import ProgramMES.Surface;

import java.io.FileNotFoundException;

public class ElementLocal {

    private double[][] dN_po_ksi;
    private double[][] dN_po_eta;

    private double[][] N;

    private NodeLocal[] nodeLocal = { //punkty calkowania gaussa
            new NodeLocal(-1.0 / Math.sqrt(3.0), -1.0 / Math.sqrt(3.0)),
            new NodeLocal(1.0 / Math.sqrt(3.0), -1.0 / Math.sqrt(3.0)),
            new NodeLocal(1.0 / Math.sqrt(3.0), 1.0 / Math.sqrt(3.0)),
            new NodeLocal(-1.0 / Math.sqrt(3.0), 1.0 / Math.sqrt(3.0))
    };

    private SurfaceLocal[] surfaceLocal = { //punkty calkowania gaussa dla powierzchni
            new SurfaceLocal(new NodeLocal(-1.0, 1.0 / Math.sqrt(3.0)), new NodeLocal(-1.0, -1.0 / Math.sqrt(3.0))),
            new SurfaceLocal(new NodeLocal(-1.0 / Math.sqrt(3.0), -1.0), new NodeLocal(1.0 / Math.sqrt(3.0), -1.0)),
            new SurfaceLocal(new NodeLocal(1.0, -1.0 / Math.sqrt(3.0)), new NodeLocal(1.0, 1.0 / Math.sqrt(3.0))),
            new SurfaceLocal(new NodeLocal(1.0 / Math.sqrt(3.0), 1.0), new NodeLocal(-1.0 / Math.sqrt(3.0), 1.0))
    };

//    private Surface[] surfaceForOneLocalElement;



    public ElementLocal() throws FileNotFoundException {
        double[] ksi = {(-1 / Math.pow(3, 0.5)), (1 / Math.pow(3, 0.5)), (-1 / Math.pow(3, 0.5)), (1 / Math.pow(3, 0.5))};
        double[] eta = {(-1 / Math.pow(3, 0.5)), (-1 / Math.pow(3, 0.5)), (1 / Math.pow(3, 0.5)), (1 / Math.pow(3, 0.5))};

        dN_po_ksi = new double[4][4];
        dN_po_eta = new double[4][4];
        N = new double[4][4];

//        surfaceForOneLocalElement = new Surface[4];

        for (int i = 0; i < 4; i++) {

            // wartosci funkcji kształtu
            N[i][0] = N1(nodeLocal[i].getKsi(), nodeLocal[i].getEta());
            N[i][1] = N2(nodeLocal[i].getKsi(), nodeLocal[i].getEta());
            N[i][2] = N3(nodeLocal[i].getKsi(), nodeLocal[i].getEta());
            N[i][3] = N4(nodeLocal[i].getKsi(), nodeLocal[i].getEta());

            dN_po_ksi[i][0] = N1_ksi(ksi[i]);
            dN_po_ksi[i][1] = N2_ksi(ksi[i]);
            dN_po_ksi[i][2] = N3_ksi(ksi[i]);

            dN_po_ksi[i][3] = N4_ksi(ksi[i]);

            dN_po_eta[i][0] = N1_eta(eta[i]);
            dN_po_eta[i][1] = N2_eta(eta[i]);
            dN_po_eta[i][2] = N3_eta(eta[i]);
            dN_po_eta[i][3] = N4_eta(eta[i]);


        }

        //macierze funkcji ksztaltu dla powierzchni
        for (int i = 0; i < 4; i++) {   // funkcje kształtu
            for (int j = 0; j < 2; j++) { // ilosc punktów
                surfaceLocal[i].N[j][0] = N1(surfaceLocal[i].nodeLocals[j].getKsi(), surfaceLocal[i].nodeLocals[j].getEta());
                surfaceLocal[i].N[j][1] = N2(surfaceLocal[i].nodeLocals[j].getKsi(), surfaceLocal[i].nodeLocals[j].getEta());
                surfaceLocal[i].N[j][2] = N3(surfaceLocal[i].nodeLocals[j].getKsi(), surfaceLocal[i].nodeLocals[j].getEta());
                surfaceLocal[i].N[j][3] = N4(surfaceLocal[i].nodeLocals[j].getKsi(), surfaceLocal[i].nodeLocals[j].getEta());
            }
        }
    }

    private static ElementLocal elementLocal = null;

    public static ElementLocal getInstance() throws FileNotFoundException {
        if (elementLocal == null) {
            elementLocal = new ElementLocal();
        }
        return elementLocal;
    }


    private double N1(double ksi, double eta) {
        return 0.25 * (1 - ksi) * (1 - eta);
    }

    private double N2(double ksi, double eta) {
        return 0.25 * (1 + ksi) * (1 - eta);
    }

    private double N3(double ksi, double eta) {
        return 0.25 * (1 + ksi) * (1 + eta);
    }

    private double N4(double ksi, double eta) {
        return 0.25 * (1 - ksi) * (1 + eta);
    }


    //
    private double N1_ksi(double eta) {
        return (-(1.0 / 4.0) * (1 - eta));
    }

    private double N1_eta(double ksi) {
        return (-(1.0 / 4.0) * (1 - ksi));
    }

    //
    private double N2_ksi(double eta) {
        return ((1.0 / 4.0) * (1 - eta));
    }

    private double N2_eta(double ksi) {
        return (-(1.0 / 4.0) * (1 + ksi));
    }

    //
    private double N3_ksi(double eta) {
        return ((1.0 / 4.0) * (1 + eta));
    }

    private double N3_eta(double ksi) {
        return ((1.0 / 4.0) * (1 + ksi));
    }

    //
    private double N4_ksi(double eta) {
        return (-(1.0 / 4.0) * (1 + eta));
    }

    private double N4_eta(double ksi) {
        return ((1.0 / 4.0) * (1 - ksi));
    }


    public double[][] getdN_po_ksi() {
        return dN_po_ksi;
    }

    public double[][] getdN_po_eta() {
        return dN_po_eta;
    }

    public double[][] getN() {
        return N;
    }

    public SurfaceLocal[] getSurfaceLocal() {
        return surfaceLocal;
    }
}
