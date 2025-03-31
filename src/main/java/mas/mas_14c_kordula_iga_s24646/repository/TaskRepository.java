package mas.mas_14c_kordula_iga_s24646.repository;

import mas.mas_14c_kordula_iga_s24646.model.God;
import mas.mas_14c_kordula_iga_s24646.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task,Long> {
}
