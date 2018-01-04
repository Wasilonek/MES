package Local;

import java.io.FileNotFoundException;

/**
 * Created by Kamil on 2017-12-31.
 */

public class LocalElement {

    // Pochodne
    private double[][] dN_po_ksi;
    private double[][] dN_po_eta;

    // Punkty całkowania
    private LocalNode[] localNode;

    // Lokalne powierzchnie
    private SurfaceLocal[] surfaceLocal;

    private double[] ksi;
    private double[] eta;

    // Wartosci funkcji kształtu dla punktów całkowania
    private double[][] N;

    public LocalElement() throws FileNotFoundException {

        dN_po_ksi = new double[4][4];
        dN_po_eta = new double[4][4];
        N = new double[4][4];


        //Wypełnianie wspórzędnych punktów całkowania
        localNode = new LocalNode[4];
        localNode[0] = new LocalNode(-1.0 / Math.sqrt(3.0), -1.0 / Math.sqrt(3.0));
        localNode[1] = new LocalNode(1.0 / Math.sqrt(3.0), -1.0 / Math.sqrt(3.0));
        localNode[2] = new LocalNode(1.0 / Math.sqrt(3.0), 1.0 / Math.sqrt(3.0));
        localNode[3] = new LocalNode(-1.0 / Math.sqrt(3.0), 1.0 / Math.sqrt(3.0));


        //Tworzenie lokalnych współrzednych dla powierzchni
        surfaceLocal = new SurfaceLocal[4];
        surfaceLocal[0] = new SurfaceLocal(new LocalNode(-1.0, 1.0 / Math.sqrt(3.0)), new LocalNode(-1.0, -1.0 / Math.sqrt(3.0)));
        surfaceLocal[1] = new SurfaceLocal(new LocalNode(-1.0 / Math.sqrt(3.0), -1.0), new LocalNode(1.0 / Math.sqrt(3.0), -1.0));
        surfaceLocal[2] = new SurfaceLocal(new LocalNode(1.0, -1.0 / Math.sqrt(3.0)), new LocalNode(1.0, 1.0 / Math.sqrt(3.0)));
        surfaceLocal[3] = new SurfaceLocal(new LocalNode(1.0 / Math.sqrt(3.0), 1.0), new LocalNode(-1.0 / Math.sqrt(3.0), 1.0));


        ksi = new double[4];
        ksi[0] = (-1 / Math.pow(3, 0.5));
        ksi[1] = (1 / Math.pow(3, 0.5));
        ksi[2] = (-1 / Math.pow(3, 0.5));
        ksi[3] = (1 / Math.pow(3, 0.5));

        eta = new double[4];
        eta[0] = (-1 / Math.pow(3, 0.5));
        eta[1] = (-1 / Math.pow(3, 0.5));
        eta[2] = (1 / Math.pow(3, 0.5));
        eta[3] = (1 / Math.pow(3, 0.5));


        for (int i = 0; i < 4; i++) {

            // Funkcje kształtu elementu czterowęzłowego
            N[i][0] = N1(localNode[i].getKsi(), localNode[i].getEta());
            N[i][1] = N2(localNode[i].getKsi(), localNode[i].getEta());
            N[i][2] = N3(localNode[i].getKsi(), localNode[i].getEta());
            N[i][3] = N4(localNode[i].getKsi(), localNode[i].getEta());

            dN_po_ksi[i][0] = N1_ksi(ksi[i]);
            dN_po_ksi[i][1] = N2_ksi(ksi[i]);
            dN_po_ksi[i][2] = N3_ksi(ksi[i]);
            dN_po_ksi[i][3] = N4_ksi(ksi[i]);

            dN_po_eta[i][0] = N1_eta(eta[i]);
            dN_po_eta[i][1] = N2_eta(eta[i]);
            dN_po_eta[i][2] = N3_eta(eta[i]);
            dN_po_eta[i][3] = N4_eta(eta[i]);
        }


        // Funkcje ksztaltu dla powierzchni
        for (int i = 0; i < 4; i++) {   // 4 - Funkcje kształtu
            for (int j = 0; j < 2; j++) { // 2 - Ilość punktów całkowania
                surfaceLocal[i].shapeFunction[j][0] = N1(surfaceLocal[i].localNode[j].getKsi(), surfaceLocal[i].localNode[j].getEta());
                surfaceLocal[i].shapeFunction[j][1] = N2(surfaceLocal[i].localNode[j].getKsi(), surfaceLocal[i].localNode[j].getEta());
                surfaceLocal[i].shapeFunction[j][2] = N3(surfaceLocal[i].localNode[j].getKsi(), surfaceLocal[i].localNode[j].getEta());
                surfaceLocal[i].shapeFunction[j][3] = N4(surfaceLocal[i].localNode[j].getKsi(), surfaceLocal[i].localNode[j].getEta());
            }
        }

    }

    private static LocalElement localElement = null;

    public static LocalElement getInstance() throws FileNotFoundException {
        if (localElement == null) {
            localElement = new LocalElement();
        }
        return localElement;
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
