package mas.mas_14c_kordula_iga_s24646.repository;

import mas.mas_14c_kordula_iga_s24646.model.Employee;
import mas.mas_14c_kordula_iga_s24646.model.God;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {
}
