import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created by Kamil on 2017-10-27.
 */
public class Node {
    private double x , y , temperatura;         // x , y - współrzędne
    private int status;                         // 0 gdy nie nakładamy na dany punkt warunku brzegowego , 1 gdy nakładamy
    int id;


    public Node(){
        x = 0 ;
        y = 0 ;
        temperatura = 0 ;
        status = 0;
    }

    @Override
    public String toString() {
        return "Node " + id + "{" +
                "x=" + x +
                ", y=" + y +
//                ", temperatura=" + temperatura +
//                ", status=" + status +
                '}';
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
