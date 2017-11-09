/**
 * Created by Kamil on 2017-11-09.
 */
public class Calka {
    public static void main(String[] args) {
        //calka2D();
        //calka3D();
    }

    static void calka2D(){
        double tab2[] = {-0.577 , 0.577};
        double w2[] = {1 , 1};

        double suma = 0;

        for(int i = 0 ; i < 2 ; i++){
            for (int j = 0 ; j < 2 ; j++){
                suma+= fun(tab2[i],tab2[j]) * w2[i] * w2[j];
            }
        }
        System.out.println("Suma 2D: " + suma);
    }

    static void calka3D(){
        double tab2[] = {-0.7745 , 0 , 0.7745};
        double w2[] = {(5.0/9.0) , (8.0/9.0) , (5.0/9.0)};

        double suma = 0;

        for(int i = 0 ; i < 3 ; i++){
            for (int j = 0 ; j < 3 ; j++){
                suma+= fun(tab2[i],tab2[j]) * w2[i] * w2[j];
            }
        }
        System.out.println("Suma 3D: " + suma);
    }

    static double fun(double x , double y){
        return (2*(x*x)*(y*y) + 6*x + 5);
    }
}
