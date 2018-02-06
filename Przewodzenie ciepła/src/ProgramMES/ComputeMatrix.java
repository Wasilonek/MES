package ProgramMES;

import Local.LocalElement;

import java.io.FileNotFoundException;

/**
 * Created by Kamil on 2017-12-31.
 */
public class ComputeMatrix {

    Grid grid = Grid.getInstance();
    GlobalData globalData = GlobalData.getInstance();

    private LocalElement LocalElement;
    private double[][] actual_H;
    private double[] actual_P;
    private double[][] global_H;
    private double[] global_P;
    private double alfa , c , k , ro;


    public ComputeMatrix() throws FileNotFoundException {
        LocalElement = LocalElement.getInstance();
        actual_H = new double[4][4];
        actual_P = new double[4];
        global_H = new double[globalData.getNh()][globalData.getNh()];
        global_P = new double[globalData.getNh()];
    }

    public void compute() throws FileNotFoundException {

        Grid grid = Grid.getInstance();
        Jakobian jakobian;

        // Zmienne które chcemy obliczyć przy Jakobianie
        double[] dNdx = new double[4];
        double[] dNdy = new double[4];

        // Wspórzędne węzła z elementu
        double[] x = new double[4];
        double[] y = new double[4];

        // Temperatura która bedzie ulegała zmianie w kroku czasowym
        double[] temperatureInTheTimeStep = new double[4];

        // Temperatura zinterpolowana ( każdy punkt całkowania jest interpolowany z kazdego węzła)
        double interpolatedTemperature;

        // Konkretna potrzebna wartość z macierzy C ( nie obliczana jest cała macierz tylko konkretna wartość która jest nam potrzebna)
        double valueFromMatrixC;

        int elementID;

        // Wyznacznik
        double detj = 0;

        for (int i = 0; i < globalData.getNh(); i++) {
            for (int j = 0; j < globalData.getNh(); j++) {
                global_H[i][j] = 0;
            }
            global_P[i] = 0;
        }

        // Pętla po wszystkich elementach
        for (int elementNumber = 0; elementNumber < globalData.getNe(); elementNumber++) { // pętla 16 po wszystkich elementach

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    actual_H[i][j] = 0;
                }
                actual_P[i] = 0;
            }

            // Dla aktualnego elementu pętli wyciagam jest dane (x , y , temperatura )
            for (int i = 0; i < 4; i++) {
                elementID = grid.elements[elementNumber].globalNode[i];
                x[i] = grid.nodes[elementID].getX();
                y[i] = grid.nodes[elementID].getY();
                temperatureInTheTimeStep[i] = grid.nodes[elementID].getTemperature();

                alfa = grid.elements[elementNumber].getAlfa();
                c = grid.elements[elementNumber].getC();
                k = grid.elements[elementNumber].getK();
                ro = grid.elements[elementNumber].getRo();
            }

            // Pętla po punktach całkowania ( 4 )
            for (int intergrationPoint = 0; intergrationPoint < 4; intergrationPoint++) {

                jakobian = new Jakobian(intergrationPoint, x, y);
                interpolatedTemperature = 0;

                for (int j = 0; j < 4; j++) {

                    // Interpolacja temperatury ( mnożenie poszczególnych temperatur i funkcji kształtu )
                    interpolatedTemperature += temperatureInTheTimeStep[j] * LocalElement.getN()[intergrationPoint][j];

                    dNdx[j] = 1.0 / jakobian.getJakobianDet() * (jakobian.getJakobianOdw()[0][0] * LocalElement.getdN_po_ksi()[intergrationPoint][j]
                            + jakobian.getJakobianOdw()[0][1] * LocalElement.getdN_po_eta()[intergrationPoint][j]);

                    dNdy[j] = (1.0 / jakobian.getJakobianDet() * (jakobian.getJakobianOdw()[1][0] * LocalElement.getdN_po_ksi()[intergrationPoint][j]
                            + jakobian.getJakobianOdw()[1][1] * LocalElement.getdN_po_eta()[intergrationPoint][j]));
                }

                detj = jakobian.getJakobianDet();

                // Używam 2 pętli aby była mozliwosc pomnozenia wektora funkcji kształtu i transponowanego wektora funkcji kształtu
                // rownanie 6.8
                // 2 petele bo 4 funkcje kształtu musimy za kazdym razem przemnozyc przez 4 kunkcje kształtu
                for (int i = 0; i < 4; i++) {// 4 pkt
                    for (int j = 0; j < 4; j++) {

                        valueFromMatrixC = c * ro * LocalElement.getN()[intergrationPoint][i] * LocalElement.getN()[intergrationPoint][j] * detj;

                        // wzor na macierz H
                        actual_H[i][j] += k * (dNdx[i] * dNdx[j] + dNdy[i] * dNdy[j]) * detj + valueFromMatrixC / globalData.getDtau();
                        actual_P[i] += valueFromMatrixC / globalData.getDtau() * interpolatedTemperature;

                    }
                }
            }


            // Warunki brzegowe na powierzchnie
            // Wykorzytuje wzór na długosc odcinka dzielony przez 2 -> deltaX / 2
            for (int ipow = 0; ipow < grid.elements[elementNumber].getTheNumberOfSurfacesThatAreInContactWithTheSurroundings(); ipow++) {

                elementID = grid.elements[elementNumber].getLocalNumberOfSurfacesThatAreInContactWithTheSurroundings()[ipow];

                if (elementID == 0) {
                    detj = Math.sqrt(Math.pow(grid.elements[elementNumber].nodes[3].getX() - grid.elements[elementNumber].nodes[0].getX(), 2)
                            + Math.pow(grid.elements[elementNumber].nodes[3].getY() - grid.elements[elementNumber].nodes[0].getY(), 2)) / 2.0;
                } else if (elementID == 1) {
                    detj = Math.sqrt(Math.pow(grid.elements[elementNumber].nodes[0].getX() - grid.elements[elementNumber].nodes[1].getX(), 2)
                            + Math.pow(grid.elements[elementNumber].nodes[0].getY() - grid.elements[elementNumber].nodes[1].getY(), 2)) / 2.0;
                } else if (elementID == 2) {
                    detj = Math.sqrt(Math.pow(grid.elements[elementNumber].nodes[1].getX() - grid.elements[elementNumber].nodes[2].getX(), 2)
                            + Math.pow(grid.elements[elementNumber].nodes[1].getY() - grid.elements[elementNumber].nodes[2].getY(), 2)) / 2.0;
                } else if (elementID == 3) {
                    detj = Math.sqrt(Math.pow(grid.elements[elementNumber].nodes[2].getX() - grid.elements[elementNumber].nodes[3].getX(), 2)
                            + Math.pow(grid.elements[elementNumber].nodes[2].getY() - grid.elements[elementNumber].nodes[3].getY(), 2)) / 2.0;
                }


                // Pętla po punktach całkowania a nastepnie po funkcjach kształtu ( kolejne mnożenie wektora transponowanego wiec 2 pętle)
                for (int p = 0; p < 2; p++) {
                    for (int n = 0; n < 4; n++) {
                        for (int n2 = 0; n2 < 4; n2++) {
                            // detj to wyznacznik dla powierzchni
                            actual_H[n][n2] += globalData.getAlfa() * LocalElement.getSurfaceLocal()[elementID].shapeFunction[p][n] * LocalElement.getSurfaceLocal()[elementID].shapeFunction[p][n2] * detj;
                        }
                        // W tym wzorze nie ma minusa ponieważ w równaniu 6.8 mamy minus
                        if(elementID == 0)
                            actual_P[n] += alfa * globalData.gettEnvironment() * LocalElement.getSurfaceLocal()[elementID].shapeFunction[p][n] * detj;
                        if(elementID == 2)
                            actual_P[n] += alfa * globalData.gettEnviromentOutsite() * LocalElement.getSurfaceLocal()[elementID].shapeFunction[p][n] * detj;
                    }
                }
            }

            // Przeprowadzam agregacje
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    global_H[grid.elements[elementNumber].globalNode[i]][grid.elements[elementNumber].globalNode[j]] += actual_H[i][j];
                }
                global_P[grid.elements[elementNumber].globalNode[i]] += actual_P[i];
            }
        }
    }

    public void showMatrixH() {
        System.out.println("Macierz [H] = [H]+[C]/dT:");
        for (int i = 0; i < globalData.getNh(); i++) {
            for (int j = 0; j < globalData.getNh(); j++) {
                System.out.print(global_H[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public void showVectorP() {
        System.out.println("Macierz [{P}+{[C]/dT}*{T0}:");
        for (int j = 0; j < globalData.getNh(); j++) {
            System.out.print(global_P[j]);
        }
        System.out.println();

    }


    public Grid getGrid() {
        return grid;
    }

    public GlobalData getGlobalData() {
        return globalData;
    }

    public Local.LocalElement getLocalElement() {
        return LocalElement;
    }

    public double[][] getActual_H() {
        return actual_H;
    }

    public double[] getActual_P() {
        return actual_P;
    }

    public double[][] getGlobal_H() {
        return global_H;
    }

    public double[] getGlobal_P() {
        return global_P;
    }
}
