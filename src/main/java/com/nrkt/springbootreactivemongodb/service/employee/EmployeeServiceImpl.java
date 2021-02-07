package com.nrkt.springbootreactivemongodb.service.employee;

import com.nrkt.springbootreactivemongodb.dto.request.EmployeeRequest;
import com.nrkt.springbootreactivemongodb.dto.response.EmployeeResponse;
import com.nrkt.springbootreactivemongodb.exception.BadRequestException;
import com.nrkt.springbootreactivemongodb.mapper.EmployeeMapper;
import com.nrkt.springbootreactivemongodb.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;

    @Override
    public Mono<EmployeeResponse> addEmployee(@NotBlank EmployeeRequest employee, ServerWebExchange exchange) {
        var newEmployee = employeeMapper.employeeRequestToEmployeeEntity(employee);
        var savedEmployee = employeeRepository.save(newEmployee);
        return savedEmployee.map(item -> employeeMapper.employeeEntityToEmployeeResponse(item, exchange)).log();
    }

    @Override
    public Mono<EmployeeResponse> updateEmployee(@NotBlank String id, EmployeeRequest employee, ServerWebExchange exchange) {
        var existEmployee = Mono.just(id)
                .flatMap(employeeRepository::findById)
                .switchIfEmpty(Mono.error(new BadRequestException("employee not found"))).log()
                .map(item -> employeeMapper.employeeRequestToEmployeeEntity(employee))
                .flatMap(employeeRepository::save).log();

        return existEmployee.map(item -> employeeMapper.employeeEntityToEmployeeResponse(item, exchange));
    }

    @Override
    public Mono<Void> removeEmployee(@NotBlank String id) {
        return employeeRepository.deleteById(id);
    }

    @Override
    public Mono<EmployeeResponse> getEmployee(@NotBlank String id, ServerWebExchange exchange) {
        return Mono.just(id)
                .flatMap(employeeRepository::findById)
                .switchIfEmpty(Mono.error(new BadRequestException("employee not found")))
                .log()
                .map(item -> employeeMapper.employeeEntityToEmployeeResponse(item, exchange));
    }

    @Override
    public Flux<EmployeeResponse> getAllEmployee(ServerWebExchange exchange) {
        return employeeRepository.findAll()
                .map(item -> employeeMapper.employeeEntityToEmployeeResponse(item, exchange));
    }
}
