package lazybro.company.factorycrm.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Stock extends Identify {

    @NotBlank(message = "Обязательно нужно указать материал")
    @Column(nullable = false)
    private String material;

    @NotBlank(message = "Обязательно нужно указать форму")
    @Column(nullable = false)
    private String form;

    private double diameter;

    private double width;

    private double length;

    private double height;

    public Stock() {
    }

    public Stock(String material, String form) {
        this.material = material;
        this.form = form;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
