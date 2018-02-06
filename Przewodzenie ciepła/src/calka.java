/**
 * Created by Kamil on 2018-01-17.
 */
public class calka {

//    static double fun(double x, double y, double z) {
//        return ((x * x * y * z) + (3 * x * y) + (6 * y * x) + 10);
//    }

//    static double fun(double x) {
//        return 3 * x * x + 3 * x + 1;
//    }

//    static double fun(double x) {
//        return 3 * x * x +3*x +1;
//    }
//
//     static double compute(double[] pc, int n, double[] waga, int r1, int r2) {
//
//        double calka = 0;
//
//
//        double tab[] = new double[n];
//
//        for (int i = 0; i < n; i++) {
//            tab[i] = 0.5 * (1 - pc[i]) * r1 + 0.5 * (1 + pc[i]) * r2;
//        }
//        for (int i = 0; i < n; i++) {
//            calka += fun(tab[i]) * waga[i];
//        }
//
//        return calka * ((r2 - r1) / 2);
//    }
//
//    public static void main(String[] args) {
//        double pkt[] = {-0.57 , 0.57};
//        double wages[] = {1,1};
//        System.out.println(compute(pkt , 2 , wages , 2,10));
//
//    }

//        double pkt[] = {-0.57 , 0.57};
//        double wages[] = {1,1};
//
//        double calka = 0.0;
//
//        double x[] = new double[3];
//        x[0] = 0.5 * (1 - 0.7745 ) * 2 + 0.5 * (1 + 0.7745) * 10;
//        x[1] = 0.5 * (1 + 0) * 2 + 0.5 * (1 - 0) * 10;
//        x[2] = 0.5 * (1 + 0.7745) * 2 + 0.5 * (1 - 0.7745) * 10;
//
////        for (int i = 0; i < 2; i++) {
////            for (int j = 0; j < 2; j++){
////                for(int k = 0 ; k < 2 ; k++){
////                    calka += fun(pkt[i], pkt[j] , pkt[k]) * wages[i] * wages[j] * wages[k];
////                }
////            }
////        }
//
////        for (int i = 0; i < 3; i++) {
////            calka += fun(x[i]) * wages[i];
////        }
//
//        for(int i = 0 ; i< 2 ; i++){
//            for(int j = 0 ; j < 2 ; j++){
//                calka += fun(pkt[i] , pkt[j]) * wages[i] * wages[i];
//            }
//        }
//
//        System.out.println(calka);
//
//    }

//    static double fun(double r) {
//        return 3 * r *r;
//    }
//
//    static double compute(double[] rVec, int n, double wVec[], double r1, double r2) {
//
//        double r[] = new double[n];
//        double calka = 0;
//
//        for (int i = 0; i < n; i++) {
//            r[i] = 0.5 * (1 - rVec[i]) * r1 + 0.5 * (1 + rVec[i]) * r2;
//        }
//
//        for (int i = 0; i < n; i++) {
//            calka += fun(r[i]) * wVec[i];
//        }
//
//        double det = 0;
//        det = ((r2 - r1) / 2);
//        calka *= det;
//
//        return calka;
//    }
//
//
//    public static void main(String[] args) {
//        double pc[] = {0,0.53,0.9};
//        double waga[] = {0.56 , 0.48 , 0.24};
//        double calka = 0;
//
//        double[] r = new double[2];
//        r[0] = (0.5 * (1 - pc[0]) * 0) + (0.5 * (1 + pc[0]) * 5);
//        r[1] = (0.5 * (1 - pc[1]) * 0) + (0.5 * (1 + pc[1]) * 5);
//
//
//        for (int i = 0; i < 2; i++) {
//            calka += r[i] * waga[i];
//        }
//        calka *= 2.5;
//
//   System.out.println(calka);
//    }

    static double fun(double x , double y , double z){
        return (x*x*y*z) + (3*x*y) + (6*y*x) + 10;
    }

    static double calka (double pkt[] , int n , double wagi[]){
        double calka = 0 ;

        for(int i = 0 ; i < 5 ; i++){
            for(int j = 0 ; j < 5 ; j++){
                for(int k = 0 ; k < 5 ; k++){
                    calka += fun(pkt[i], pkt[j],pkt[k]) * wagi[i] * wagi[j] * wagi[k];
                }
            }
        }
        return calka;
    }

    public static void main(String[] args){
        double pkt[] = {-0.9 , -0.53 , 0 , 0.53 , 0.9};
        double wagi[] = {0.24 , 0.48 , 0.56 , 0.48 , 0.24};


        System.out.println(calka(pkt , 5 , wagi));
    }

}
