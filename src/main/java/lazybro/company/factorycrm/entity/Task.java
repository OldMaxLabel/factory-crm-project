package lazybro.company.factorycrm.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Task extends Identify {

    @NotBlank(message = "Обязательно должно быть указано наименование")
    @Column(nullable = false)
    private String title;

    @Column(length = 350)
    private String comments;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate initialize_date;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finalize_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_date;

    @Min(value = 1, message = "Обязательно должно быть указано количество")
    @Column(nullable = false)
    private int count;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToMany
    @JoinTable(name = "tasks_machines",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "machine_id"))
    private Set<Machine> machines;

    @ManyToMany
    @JoinTable(name = "tasks_tools",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private Set<Tool> tools;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Operation> operations = new ArrayList<>();

    @NotBlank(message = "Обязательно должен быть прикреплен чертеж")
    @Column(nullable = false)
    private String filename;

    public Task() {
    }

    public Task(String title, LocalDate initialize_date, LocalDate finalize_date, int count) {
        this.title = title;
        this.initialize_date = initialize_date;
        this.finalize_date = finalize_date;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getInitialize_date() {
        return initialize_date;
    }

    public void setInitialize_date(LocalDate initialize_date) {
        this.initialize_date = initialize_date;
    }

    public LocalDate getFinalize_date() {
        return finalize_date;
    }

    public void setFinalize_date(LocalDate finalize_date) {
        this.finalize_date = finalize_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<Machine> getMachines() {
        return machines;
    }

    public void setMachines(Set<Machine> machines) {
        this.machines = machines;
    }

    public Set<Tool> getTools() {
        return tools;
    }

    public void setTools(Set<Tool> tools) {
        this.tools = tools;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
