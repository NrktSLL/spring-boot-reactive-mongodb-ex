package com.nrkt.springbootreactivemongodb.service.employee;


import com.nrkt.springbootreactivemongodb.dto.request.EmployeeRequest;
import com.nrkt.springbootreactivemongodb.dto.response.EmployeeResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<EmployeeResponse> addEmployee(EmployeeRequest employee, ServerWebExchange exchange);

    Mono<EmployeeResponse> updateEmployee(String id, EmployeeRequest employee, ServerWebExchange exchange);

    Mono<Void> removeEmployee(String id);

    Mono<EmployeeResponse> getEmployee(String id, ServerWebExchange exchange);

    Flux<EmployeeResponse> getAllEmployee(ServerWebExchange exchange);
}
