package lazybro.company.factorycrm.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Operation extends Identify {

    @Column(nullable = false)
    private String title;

    @Min(value = 1, message = "Не менее 1 минуты")
    @Column(nullable = false)
    private int duration;

    @ManyToOne
    private Task task;

    public Operation() {
    }

    public Operation(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
