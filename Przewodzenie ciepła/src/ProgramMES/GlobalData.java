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
    private double dtau1 , dtau2 , dtau;            //poczatkowa wartosc przyrostu czasu
    private double tEnvironment, tEnviromentOutsite;    //temperatura otoczenia
    private double alfa, alfaOutsite;            //wspolczynnik wymiany ciepla
    private double c, cOutsite;               //cieplo wlasciwe
    private double k, kOutsite;               //wspolczynnik przewodzenia ciepla
    private double ro, roOutsite;              //gestosc
    private double asr1 , asr2 , asr;


    private double dB;
    private double dH;
    private int nh;
    private int ne;


    private GlobalData() throws FileNotFoundException {
        Scanner input = new Scanner(new File("data2.txt"));
        input.hasNextDouble();


        this.H = input.nextDouble();
        input.nextLine();
        this.B = input.nextDouble();
        input.nextLine();
        this.nH = input.nextInt();
        input.nextLine();
        this.nB = input.nextInt();
        input.nextLine();
        this.t_begin = input.nextDouble();
        input.nextLine();
        this.tau = input.nextDouble();
        input.nextLine();
        this.dtau = input.nextDouble();
        input.nextLine();
        this.tEnvironment = input.nextDouble();
        input.nextLine();
        this.tEnviromentOutsite = input.nextDouble();
        input.nextLine();
        this.alfa = input.nextDouble();
        input.nextLine();
        this.alfaOutsite = input.nextDouble();
        input.nextLine();
        this.c = input.nextDouble();
        input.nextLine();
        this.cOutsite = input.nextDouble();
        input.nextLine();
        this.k = input.nextDouble();
        input.nextLine();
        this.kOutsite = input.nextDouble();
        input.nextLine();
        this.ro = input.nextDouble();
        input.nextLine();
        this.roOutsite = input.nextDouble();
        input.close();

//        asr1 = (k / (ro * c));
////        dtau1 = (Math.pow((B / nB), 2)) / (0.5 * asr1);
//
//        asr2 = (kOutsite/(roOutsite*cOutsite));
//        asr = (asr1+asr2)/2;
//        dtau = (Math.pow((B/nB), 2) / (0.5*asr));



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

    public double gettEnviromentOutsite() {
        return tEnviromentOutsite;
    }

    public double getAlfaOutsite() {
        return alfaOutsite;
    }

    public double getcOutsite() {
        return cOutsite;
    }

    public double getkOutsite() {
        return kOutsite;
    }

    public double getRoOutsite() {
        return roOutsite;
    }

    public static GlobalData getGlobalData() {
        return globalData;
    }
}
