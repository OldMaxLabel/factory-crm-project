package lazybro.company.factorycrm.service;

import lazybro.company.factorycrm.entity.Stock;
import lazybro.company.factorycrm.entity.Stuff;
import lazybro.company.factorycrm.entity.Task;
import lazybro.company.factorycrm.repository.StockRepository;
import lazybro.company.factorycrm.repository.StuffRepository;
import lazybro.company.factorycrm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskStockService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StuffRepository stuffRepository;


    public boolean addNewStock(long id, Stock stock) {

        Task task = taskRepository.findById(id).orElseThrow();

        if (stock.getDiameter() != 0.0 && stock.getLength() != 0.0 && stock.getHeight() == 0.0 && stock.getWidth() == 0.0) {

            stockRepository.save(stock);

        } else if (stock.getDiameter() == 0.0 && stock.getHeight() != 0.0 && stock.getWidth() != 0.0 && stock.getLength() != 0.0) {

            stockRepository.save(stock);

        } else {
            return false;
        }

        task.setStock(stock);

        taskRepository.save(task);

        return true;
    }

    public boolean pushStuff(long task_id, long stock_id) {

        //*******************************************************
        // расчет длины материала по заготовке и количеству
        // вычитание из общего количества материала со склада

        Task task = taskRepository.findById(task_id).orElseThrow();
        Stock stock = stockRepository.findById(stock_id).orElseThrow();

        Stuff stuff;

        if (stock.getDiameter() != 0.0) {
            stuff = stuffRepository.findByFormAndMaterialAndDiameter(stock.getForm(), stock.getMaterial(), stock.getDiameter());
        } else {
            stuff = stuffRepository.findByFormAndMaterialAndHeightAndWidth(stock.getForm(),
                    stock.getMaterial(), stock.getHeight(), stock.getWidth());
        }

        if (null != stuff && stuff.getLength() != 0.0 && (stuff.getLength() - stock.getLength() * task.getCount()) >= 0) {
            stuff.setLength(Math.max(
                    (stuff.getLength() - stock.getLength() * task.getCount()), 0)
            );
            stuffRepository.save(stuff);
        } else {
            return false;
        }

        //*****************************************************
        return true;
    }

    public Stock findTaskStockById(long stock_id) {

        Stock stockFromDb = stockRepository.findById(stock_id).orElseThrow();

        return stockFromDb;
    }

    public void updateTaskStock(long stock_id, Stock stock) {

        Stock stockFromDb = stockRepository.findById(stock_id).orElseThrow();

        stockFromDb.setDiameter(stock.getDiameter());
        stockFromDb.setForm(stock.getForm());
        stockFromDb.setHeight(stock.getHeight());
        stockFromDb.setLength(stock.getLength());
        stockFromDb.setWidth(stock.getWidth());
        stockFromDb.setMaterial(stock.getMaterial());

        stockRepository.save(stockFromDb);

    }
}
