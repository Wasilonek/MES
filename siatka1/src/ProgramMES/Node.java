package ProgramMES;

import java.io.FileNotFoundException;

public class Node {
    private double x, y, temperature;
    int status;
    private GlobalData globalData;

    public Node ( double x, double y ) throws FileNotFoundException {
         globalData = GlobalData.getInstance();

        this.x = x;
        this.y = y;
        this.temperature = globalData.getT_begin();

        if (this.x == 0.0 || this.y == 0.0 || this.x == globalData.getB() || this.y == globalData.getH()) {
            this.status = 1;
        } else {
            this.status = 0;
        }
    }

//***********************************************************************************************************************


    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getStatus () {
        return status;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setT(double t) {
        this.temperature = t;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
