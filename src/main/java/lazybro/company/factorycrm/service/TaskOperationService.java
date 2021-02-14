package lazybro.company.factorycrm.service;

import lazybro.company.factorycrm.entity.Operation;
import lazybro.company.factorycrm.entity.Task;
import lazybro.company.factorycrm.repository.OperationRepository;
import lazybro.company.factorycrm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskOperationService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private OperationRepository operationRepository;


    public void addOperation(long id, String action, int time) {

        Task task = taskRepository.findById(id).orElseThrow();

        Operation operation = new Operation(action, time);

        operation.setTask(task);

        operationRepository.save(operation);

        task.getOperations().add(operation);

        taskRepository.save(task);

    }

    public void deleteOperation(long task_id, long operation_id) {

        Task task = taskRepository.findById(task_id).orElseThrow();

        Operation operation = operationRepository.findById(operation_id).orElseThrow();

        task.getOperations().remove(operation);

        taskRepository.save(task);

        operationRepository.delete(operation);

    }

    public Operation findOperationById(long operation_id) {

        Operation operation = operationRepository.findById(operation_id).orElseThrow();

        return operation;
    }

    public void editOperation(long task_id, long operation_id, String action, Operation operation) {

        Task task = taskRepository.findById(task_id).orElseThrow();

        Operation operationFromDb = operationRepository.findById(operation_id).orElseThrow();

        operationFromDb.setTitle(action);
        operationFromDb.setDuration(operation.getDuration());
        operationFromDb.setTask(task);

        operationRepository.save(operationFromDb);

    }
}
