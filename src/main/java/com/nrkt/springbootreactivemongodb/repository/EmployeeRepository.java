package com.nrkt.springbootreactivemongodb.repository;


import com.nrkt.springbootreactivemongodb.domain.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee,String> {
}
