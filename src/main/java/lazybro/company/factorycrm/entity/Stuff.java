package lazybro.company.factorycrm.entity;

import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Stuff extends Identify {

    @NotBlank(message = "Обязательно должен быть указан материал")
    @Column(nullable = false)
    private String material;

    @NotBlank(message = "Обязательно должна быть указана форма")
    @Column(nullable = false)
    private String form;

    private double diameter;

    private double length;

    private double height;

    private double width;

    public Stuff() {
    }

    public Stuff(String material, String form) {
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
