package com.codingshuttle.tutorial.dataMapping.DataMappingTutorial.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

@OneToOne(mappedBy = "manager")
//Here, mappedBy = "manager" indicates that EmployeeEntity does not own the relationship.
// Instead, the manager field in DepartmentEntity owns it. This is crucial for JPA to understand how to manage the relationship,
// especially when it comes to persisting and fetching data.
@JsonIgnore
    private DepartmentEntity managedDepartment;

@ManyToOne(cascade = CascadeType.ALL)
//as many employee can work in single department
//and EmployeeEntity is the owner of the relationship as it has the foregin key
@JoinColumn(name="worker_department_id",referencedColumnName = "id")
@JsonIgnore
private DepartmentEntity workerDepartment;


@ManyToMany
@JoinTable(name="freelancer_department_mapping",joinColumns = @JoinColumn(name="employee_id"),
 inverseJoinColumns = @JoinColumn(name="department_id"))
@JsonIgnore
private Set<DepartmentEntity>  freelanceDepartments;
//as a freelancer can work in multiple departments

}
