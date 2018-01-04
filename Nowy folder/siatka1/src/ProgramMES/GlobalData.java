package ProgramMES;

import Local.LocalElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Kamil on 2017-12-31.
 */
public class GlobalData {
    //Kolejność wczytywania z pliku
    private double H;               //wysokosc przekroju
    private double B;               //szerokosc przekroju
    private int nH;                 //liczba wezlow po wysokosci
    private int nB;                 //liczba wezlow po szerokosci
    private double t_begin;         //temperatura początkowa
    private double tau;             //czas procesu
    private double dtau;            //poczatkowa wartosc przyrostu czasu
    private double tEnvironment;    //temperatura otoczenia
    private double alfa;            //wspolczynnik wymiany ciepla
    private double c;               //cieplo wlasciwe
    private double k;               //wspolczynnik przewodzenia ciepla
    private double ro;              //gestosc


    private double dB;
    private double dH;
    private int nh;
    private int ne;


    private GlobalData() throws FileNotFoundException {
        Scanner input = new Scanner(new File("data.txt"));
        input.hasNextDouble();

        this.H = input.nextDouble();

        this.B = input.nextDouble();

        this.nH = input.nextInt();

        this.nB = input.nextInt();

        this.t_begin = input.nextDouble();

        this.tau = input.nextDouble();

        this.dtau = input.nextDouble();

        this.tEnvironment = input.nextDouble();

        this.alfa = input.nextDouble();

        this.c = input.nextDouble();

        this.k = input.nextDouble();

        this.ro = input.nextDouble();
        input.close();

        dB = B / (nB - 1);
        dH = H / (nH - 1);

        ne = (nH - 1) * (nB - 1);
        nh = nH * nB;

    }

    private static GlobalData globalData = null;

    public static GlobalData getInstance() throws FileNotFoundException {
        if (globalData == null) {
            globalData = new GlobalData();
        }
        return globalData;
    }


    public double getH() {
        return H;
    }

    public double getB() {
        return B;
    }

    public int getnH() {
        return nH;
    }

    public int getnB() {
        return nB;
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

    public double gettEnvironment() {
        return tEnvironment;
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

    public double getdB() {
        return dB;
    }

    public double getdH() {
        return dH;
    }

    public int getNh() {
        return nh;
    }

    public int getNe() {
        return ne;
    }

    public static GlobalData getGlobalData() {
        return globalData;
    }
}
