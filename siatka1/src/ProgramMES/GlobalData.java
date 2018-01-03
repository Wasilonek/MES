package ProgramMES;

import Local.ElementLocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GlobalData {
    //Kolejność wczytywania z pliku
    private double H;               //wysokosc przekroju
    private double B;               //szerokosc przekroju
    private int nH;                 //liczba wezlow po wysokosci
    private int nB;                 //liczba wezlow po szerokosci
    private double t_begin;         //temperatura początkowa
    private double tau;             //czas procesu
    private double dtau;            //poczatkowa wartosc przyrostu czasu
    private double t_otoczenia;     //temperatura otoczenia
    private double alfa;            //wspolczynnik wymiany ciepla
    private double c;               //cieplo wlasciwe
    private double k;               //wspolczynnik przewodzenia ciepla
    private double ro;              //gestosc



    private double dB;
    private double dH;
    private int nh;
    private int ne;


    private ElementLocal el_lok;
    private double[][] H_current;
    private double[] P_current;
    private double[][] H_global;
    private double[] P_global;

    private GlobalData () throws FileNotFoundException {
        Scanner input = new Scanner(new File("data.txt"));
        input.hasNextDouble();

        this.H = input.nextDouble();

        this.B = input.nextDouble();

        this.nH = input.nextInt();

        this.nB = input.nextInt();

        this.t_begin = input.nextDouble();

        this.tau = input.nextDouble();

        this.dtau = input.nextDouble();

        this.t_otoczenia = input.nextDouble();

        this.alfa = input.nextDouble();

        this.c = input.nextDouble();

        this.k = input.nextDouble();

        this.ro = input.nextDouble();
        input.close();

            dB = B / ( nB - 1 );
            dH = H / ( nH - 1 );

            ne = ( nH - 1 ) * ( nB - 1 );
            nh = nH * nB;

        el_lok = ElementLocal.getInstance();
        H_current = new double[4][4];
        P_current = new double[4];
        H_global = new double[nh][nh];
        P_global = new double[nh];

    }

    public void computeMetrix() throws FileNotFoundException {

        for (int i = 0; i < nh; i++) {
            for (int j = 0; j < nh; j++) {
                H_global[i][j] = 0;
            }
            P_global[i] = 0;
        }

        Grid grid = Grid.getInstance();
        Jakobian jakobian;
        double[] dNdx = new double[4];
        double[] dNdy = new double[4];
        double[] x = new double[4];
        double[] y = new double[4];
        double[] temp_0 = new double[4]; // temeratura w danej chwili (po zmianie czasu)
        double t0p, cij; // , konkretrna wartosc z macierzy C
        int id;
        double detj = 0;

        for (int el_nr = 0; el_nr < ne; el_nr++) { // ptla 25 po wszystkich elementach

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    H_current[i][j] = 0;
                }
                P_current[i] = 0;
            }

            for (int i = 0; i < 4; i++) {
                id = grid.EL[el_nr].globalNodeId[i];
                x[i] = grid.ND[id].getX();
                y[i] = grid.ND[id].getY();
                temp_0[i] = grid.ND[id].getTemperature();
            }

            for (int pc = 0; pc < 4; pc++) { // 4 - liczba punktow calkowania po powierzchni w elemencie
                jakobian = new Jakobian(pc, x, y);
                t0p = 0;

                for (int j = 0; j < 4; j++) { // 4 - liczba wezlow w wykorzystywanym elemencie skonczonym
                    dNdx[j] = 1.0 / jakobian.getGlobalJacobianDet() * (jakobian.getGlobalJacobian_odw()[0][0] * el_lok.getdN_po_ksi()[pc][j]
                            + jakobian.getGlobalJacobian_odw()[0][1] * el_lok.getdN_po_eta()[pc][j]);

                    dNdy[j] = (1.0 / jakobian.getGlobalJacobianDet() * (jakobian.getGlobalJacobian_odw()[1][0] * el_lok.getdN_po_ksi()[pc][j]
                            + jakobian.getGlobalJacobian_odw()[1][1] * el_lok.getdN_po_eta()[pc][j]));

                    t0p += temp_0[j] * el_lok.getN()[pc][j];
                }
                detj = Math.abs(jakobian.getGlobalJacobianDet());

                // rownanie 6.8
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        cij = c * ro * el_lok.getN()[pc][i] * el_lok.getN()[pc][j] * detj;
                        H_current[i][j] += k * (dNdx[i] * dNdx[j] + dNdy[i] * dNdy[j]) * detj + cij / dtau;
                        P_current[i] += cij / dtau * t0p;
                    }
                }
            }


            //warunki brzegowe
            for (int ipow = 0; ipow < grid.EL[el_nr].getTheNumberOfSurfacesThatAreInContactWithTheSurroundings(); ipow++) {
                id = grid.EL[el_nr].getLocalNumberOfSurfacesThatAreInContactWithTheSurroundings()[ipow];
                switch (id) {
                    case 0:
                        detj = Math.sqrt(Math.pow(grid.EL[el_nr].nodeId[3].getX() - grid.EL[el_nr].nodeId[0].getX(), 2)
                                + Math.pow(grid.EL[el_nr].nodeId[3].getY() - grid.EL[el_nr].nodeId[0].getY(), 2)) / 2.0;
                        break;
                    case 1:
                        detj = Math.sqrt(Math.pow(grid.EL[el_nr].nodeId[0].getX() - grid.EL[el_nr].nodeId[1].getX(), 2)
                                + Math.pow(grid.EL[el_nr].nodeId[0].getY() - grid.EL[el_nr].nodeId[1].getY(), 2)) / 2.0;
                        break;
                    case 2:
                        detj = Math.sqrt(Math.pow(grid.EL[el_nr].nodeId[1].getX() - grid.EL[el_nr].nodeId[2].getX(), 2)
                                + Math.pow(grid.EL[el_nr].nodeId[1].getY() - grid.EL[el_nr].nodeId[2].getY(), 2)) / 2.0;
                        break;
                    case 3:
                        detj = Math.sqrt(Math.pow(grid.EL[el_nr].nodeId[2].getX() - grid.EL[el_nr].nodeId[3].getX(), 2)
                                + Math.pow(grid.EL[el_nr].nodeId[2].getY() - grid.EL[el_nr].nodeId[3].getY(), 2)) / 2.0;
                        break;
                }

                for (int p = 0; p < 2; p++) {// w zalezonosc od punktó csalk
                    for (int n = 0; n < 4; n++) {//w zaleznosci od ilosc f ksztrałtu
                        for (int i = 0; i < 4; i++) {
                            H_current[n][i] += alfa * el_lok.getSurfaceLocal()[id].N[p][n] * el_lok.getSurfaceLocal()[id].N[p][i] * detj;
                        }
                        P_current[n] += alfa * t_otoczenia * el_lok.getSurfaceLocal()[id].N[p][n] * detj;
                    }
                }
            }
            //agregacja
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    H_global[grid.EL[el_nr].globalNodeId[i]][grid.EL[el_nr].globalNodeId[j]] += H_current[i][j];
                }
                P_global[grid.EL[el_nr].globalNodeId[i]] += P_current[i];
            }



        }
    }

    public  void showMatrixH(){
        System.out.println("Macierz [H] = [H]+[C]/dT:");
        for(int i = 0 ; i < nh ; i++){
            for(int j = 0 ; j < nh ; j ++){
                System.out.print(H_global[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public  void showVectorP(){
        System.out.println("Macierz [{P}+{[C]/dT}*{T0}:");
            for(int j = 0 ; j < nh ; j ++){
                System.out.print(P_global[j]);
            }
            System.out.println();
    }


    private static GlobalData globalData = null;
    public static GlobalData getInstance() throws FileNotFoundException {
        if (globalData == null) {
            globalData = new GlobalData();
        }
        return globalData;
    }


    public double getH () {
        return H;
    }

    public double getB () {
        return B;
    }

    public int getnH () {
        return nH;
    }

    public int getnB () {
        return nB;
    }

    public double getdB () {
        return dB;
    }

    public double getdH () {
        return dH;
    }

    public int getNh () {
        return nh;
    }

    public int getNe () {
        return ne;
    }

    public double getT_begin() {
        return t_begin;
    }

    public double getTau() {
        return tau;
    }

    public double getDtau() {
        return dtau;
    }

    public double getT_otoczenia() {
        return t_otoczenia;
    }

    public double getAlfa() {
        return alfa;
    }

    public double getC() {
        return c;
    }

    public double getK() {
        return k;
    }

    public double getRo() {
        return ro;
    }

    public ElementLocal getEl_lok() {
        return el_lok;
    }

    public double[][] getH_current() {
        return H_current;
    }

    public double[] getP_current() {
        return P_current;
    }

    public double[][] getH_global() {
        return H_global;
    }

    public double[] getP_global() {
        return P_global;
    }

    public static GlobalData getGlobalData() {
        return globalData;
    }
}
