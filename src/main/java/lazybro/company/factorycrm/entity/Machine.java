package lazybro.company.factorycrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Machine extends Identify {

    @NotBlank(message = "Наименование должно быть заполнено")
    @Column(nullable = false, length = 50)
    private String title;

    @NotBlank(message = "Обязательно нужно указать назначение")
    @Column(nullable = false)
    private String processing;

    @Min(value = 1, message = "Расход не может быть нулевым")
    @Column(name = "x_axis", nullable = false)
    private int xAxis;

    @Min(value = 1, message = "Расход не может быть нулевым")
    @Column(name = "y_axis", nullable = false)
    private int yAxis;

    @Min(value = 1, message = "Расход не может быть нулевым")
    @Column(name = "z_axis", nullable = false)
    private int zAxis;

    @NotBlank(message = "Обязательно нужно указать состояние")
    @Column(nullable = false)
    private String state;

    @Column(length = 300)
    private String description;

    @ManyToMany(mappedBy = "machines")
    private Set<Task> tasks;

    public Machine() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public int getxAxis() {
        return xAxis;
    }

    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    public int getzAxis() {
        return zAxis;
    }

    public void setzAxis(int zAxis) {
        this.zAxis = zAxis;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
