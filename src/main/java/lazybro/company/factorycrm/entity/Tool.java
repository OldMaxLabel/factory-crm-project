package lazybro.company.factorycrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import java.util.Set;

@Entity
public class Tool extends Identify {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false, precision = 2, scale = 1)
    private double diameter;

    @Column(nullable = false, precision = 2, scale = 1)
    private double length;

    @Column(name = "cutting_length", nullable = false, precision = 2, scale = 1)
    private double cuttingLength;

    @Min(value = 0)
    @Column(nullable = false)
    private int count;

    @ManyToMany(mappedBy = "tools")
    private Set<Task> tasks;

    public Tool() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public double getCuttingLength() {
        return cuttingLength;
    }

    public void setCuttingLength(double cuttingLength) {
        this.cuttingLength = cuttingLength;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
