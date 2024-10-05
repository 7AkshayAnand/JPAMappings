package com.codingshuttle.tutorial.dataMapping.DataMappingTutorial.services;

import com.codingshuttle.tutorial.dataMapping.DataMappingTutorial.entities.DepartmentEntity;
import com.codingshuttle.tutorial.dataMapping.DataMappingTutorial.entities.EmployeeEntity;
import com.codingshuttle.tutorial.dataMapping.DataMappingTutorial.repositories.DepartmentRepository;
import com.codingshuttle.tutorial.dataMapping.DataMappingTutorial.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public DepartmentEntity createNewDepartment(DepartmentEntity departmentEntity) {
        return departmentRepository.save(departmentEntity);
    }

    public DepartmentEntity getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

     public DepartmentEntity assignManagerToDepartment(Long deparmentId,Long employeeId){
       Optional< DepartmentEntity> departmentEntity=departmentRepository.findById(deparmentId);
        Optional<EmployeeEntity> employeeEntity=employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department->
                employeeEntity.map(employee->{
//                    Direct Reference: In a one-to-one relationship, you typically keep a reference to the entire entity (EmployeeEntity) rather than just the ID. This allows you to leverage the relationships defined by JPA/Hibernate.
                    department.setManager(employee);
                    return departmentRepository.save(department);
                })).orElse(null);

     }

     public DepartmentEntity getAssignedDepartmentOfmanager(Long employeeId){
        Optional<EmployeeEntity> employeeEntity=employeeRepository.findById(employeeId);
        return departmentRepository.findByManager(employeeEntity.get());
     }

    public DepartmentEntity assignWorkerToDepartment(Long departmentId, Long employeeId) {
          Optional<DepartmentEntity >  departmentEntity=departmentRepository.findById(departmentId);
          Optional<EmployeeEntity>  employeeEntity=employeeRepository.findById(employeeId);

          return departmentEntity.flatMap(department->
                  employeeEntity.map(employee->{
                              employee.setWorkerDepartment(department);
                              employeeRepository.save(employee);
                              department.getWorkers().add(employee);
                              return department;

                          })).orElse(null);
    }

    public DepartmentEntity assignFreelancerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity >  departmentEntity=departmentRepository.findById(departmentId);
        Optional<EmployeeEntity>  employeeEntity=employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department->
                employeeEntity.map(employee->{
                    employee.getFreelanceDepartments().add(department);
                    employeeRepository.save(employee);
                   department.getFreelancers().add(employee);
//                   departmentRepository.save(department);
                   return department;


                })).orElse(null);

    }
}








