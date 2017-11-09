/**
 * Created by Kamil on 2017-10-27.
 */


public class Grid {
    GlobalData globalData = new GlobalData();
    private int nh = globalData.getNh();
    private int ne = globalData.getNe();

    Node[] ND = new Node[nh];


    Element element[] = new Element[ne];

    double skok = globalData.getH() / (globalData.getnH()-1);

    double X = 0, Y = 0;

    public Grid() {

        for (int i = 0; i < ND.length; i++) {
            ND[i] = new Node();
        }

        for (int i = 0; i < nh; i++) {
            if (i % 5 == 0 && i != 0) {
                X = X + skok;
                Y = 0;
            }
            ND[i].id = i;
            ND[i].setX(X);
            ND[i].setY(Y);
            ND[i].setTemperatura(0);
            ND[i].setStatus(0);
            Y = Y + skok;

            if (ND[i].getX() == 0 || ND[i].getY() == 0 || ND[i].getX() == 0.1 || ND[i].getY() == 0.1){
                ND[i].setStatus(1);
            }
        }

        for (int i = 0; i < nh; i++) {
            System.out.println(ND[i]);
        }

        System.out.println();

        int i = 0 ;
        int licznik = 0;
        while (i < 19){
            if( (i + globalData.getnH())%globalData.getnH() == 0 && i!=0){
                i++;
            }
            element[licznik++] = new Element(ND[i],ND[i+5],ND[i+6],ND[i+1]);
            System.out.println();
            i++;
        }

        for (int k = 0 ; k < ne ; k++){
            System.out.println(element[k]);
        }


    }


}
