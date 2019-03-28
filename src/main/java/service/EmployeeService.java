package service;

import exception.BoardNotFoundException;
import model.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    @Autowired private EmployeeRepository employeeRepository;

    public Long AddBoard(EmployeeEntity entity){
        return employeeRepository.AddEmployee(entity);
    }

    public List<EmployeeEntity> ListBoards(){
        return employeeRepository.ListBoards();
    }

    public EmployeeEntity GetBoard(Long id) throws BoardNotFoundException {
        return employeeRepository.GetBoard(id);
    }
}
