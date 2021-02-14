package lazybro.company.factorycrm.service;

import lazybro.company.factorycrm.entity.Stock;
import lazybro.company.factorycrm.entity.Stuff;
import lazybro.company.factorycrm.entity.Task;
import lazybro.company.factorycrm.repository.StockRepository;
import lazybro.company.factorycrm.repository.StuffRepository;
import lazybro.company.factorycrm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StuffRepository stuffRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Iterable<Task> findAllTasks() {

        Iterable<Task> tasks = taskRepository.findAll();

        return tasks;
    }

    public Task findTaskById(long id) {

        Task task = taskRepository.findById(id).orElseThrow();

        return task;
    }

    public void removeTask(long id) {

        Task task = taskRepository.findById(id).orElseThrow();

        taskRepository.delete(task);

    }

    public void addNewTask(String title, LocalDate initialize_date, LocalDate finalize_date,
                           int count, MultipartFile file) throws IOException {

        Task task = new Task(title, initialize_date, finalize_date, count);

        addPicture(task, file);

        taskRepository.save(task);

    }

    public void updateTask(Task task, String title, LocalDate initialize_date,
                           LocalDate finalize_date, int count, String comments, MultipartFile file) throws IOException {

        task.setTitle(title);
        task.setInitialize_date(initialize_date);
        task.setFinalize_date(finalize_date);
        task.setCount(count);
        task.setComments(comments);

        addPicture(task, file);

        taskRepository.save(task);

    }

    public void addPicture(Task task, MultipartFile file) throws IOException {

        if (file != null && !file.getOriginalFilename().isEmpty()) {

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            System.out.println(uploadPath);
            System.out.println(uploadPath + "/" + resultFilename);

            task.setFilename(resultFilename);
        }

    }

}
