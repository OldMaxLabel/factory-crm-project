package lazybro.company.factorycrm.service;

import lazybro.company.factorycrm.entity.Machine;
import lazybro.company.factorycrm.entity.Task;
import lazybro.company.factorycrm.repository.MachineRepository;
import lazybro.company.factorycrm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskMachineService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MachineRepository machineRepository;


    public Iterable<Machine> findMachinesByFilter(String filter_process) {

        Iterable<Machine> machines;

        if (filter_process != null && !filter_process.isEmpty()) {
            machines = machineRepository.findByProcessing(filter_process);
        } else {
            machines = machineRepository.findAll();
        }

        return machines;
    }

    public void addMachine(long task_id, long mech_id) {

        Task task = taskRepository.findById(task_id).orElseThrow();

        Machine machine = machineRepository.findById(mech_id).orElseThrow();

        task.getMachines().add(machine);

        taskRepository.save(task);

    }

    public void removeMachine(long task_id, long mech_id) {

        Task task = taskRepository.findById(task_id).orElseThrow();

        Machine machine = machineRepository.findById(mech_id).orElseThrow();

        task.getMachines().remove(machine);

        taskRepository.save(task);

    }

}
