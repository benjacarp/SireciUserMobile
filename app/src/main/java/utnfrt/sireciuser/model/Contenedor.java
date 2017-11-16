package utnfrt.sireciuser.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by ASUS on 14/11/2017.
 */

public class Contenedor implements Serializable{

    @Expose
    private int id;
    @Expose
    private int x;
    @Expose
    private int y;
    @Expose
    private String material;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Contenedor{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", material='" + material + '\'' +
                '}';
    }
}
