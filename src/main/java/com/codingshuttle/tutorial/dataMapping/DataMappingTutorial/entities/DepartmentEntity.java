package com.codingshuttle.tutorial.dataMapping.DataMappingTutorial.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departments")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @OneToOne
//    it will automatically takes id column of EmployeeEntity as foregin key
    @JoinColumn(name="departments_manager")
    private EmployeeEntity manager;

  @OneToMany(mappedBy = "workerDepartment", fetch=FetchType.LAZY)
// mappedBy tells that workerDepartment in the EmployeeEntity is owner of this relationship
//  as it has the foreign key

  private Set<EmployeeEntity> workers;

  @ManyToMany(mappedBy = "freelanceDepartments")
  private Set<EmployeeEntity>  freelancers;
//  as one department has multiple freelancers
}
