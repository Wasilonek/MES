import java.io.*;
import java.util.Scanner;

/**
 * Created by Kamil on 2017-10-27.
 */
public class GlobalData {
    private double H, B;             // H - wysokość , B - szerokość

    private int nh, ne, nH, nB;              // nH - ilość węzłów po wysokośc , inB - ilość węzłów po szerokości
    // nh - ilość wszystkich węzłów , nb - liczba wszystkich elementów


    public GlobalData() {

        try {
            readFile("Data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        nh = nH * nB;
        ne = (nH - 1) * (nB - 1);



    }

    public void readFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        H = Double.parseDouble(bufferedReader.readLine());
        B = Double.parseDouble(bufferedReader.readLine());

        nH = Integer.parseInt(bufferedReader.readLine());
        nB = Integer.parseInt(bufferedReader.readLine());

        bufferedReader.close();

    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    public int getNh() {
        return nh;
    }

    public void setNh(int nh) {
        this.nh = nh;
    }

    public int getNe() {
        return ne;
    }

    public void setNe(int ne) {
        this.ne = ne;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnB() {
        return nB;
    }

    public void setnB(int nB) {
        this.nB = nB;
    }
}
