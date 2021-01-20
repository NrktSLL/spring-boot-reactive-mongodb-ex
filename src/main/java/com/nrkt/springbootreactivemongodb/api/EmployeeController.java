package com.nrkt.springbootreactivemongodb.api;

import com.nrkt.springbootreactivemongodb.dto.request.EmployeeRequest;
import com.nrkt.springbootreactivemongodb.dto.response.EmployeeResponse;
import com.nrkt.springbootreactivemongodb.service.employee.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/v1/employees")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeController {

    EmployeeService employeeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<EmployeeResponse> getEmployeeList(ServerWebExchange exchange) {
        return employeeService.getAllEmployee(exchange);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmployeeResponse> createEmployee(
            @NotNull @RequestBody @Valid EmployeeRequest employeeRequest,
            ServerWebExchange exchange) {

        return employeeService.addEmployee(employeeRequest, exchange);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<EmployeeResponse> editEmployee(
            @PathVariable @NotNull String id,
            @RequestBody @NotNull @Valid EmployeeRequest request,
            ServerWebExchange exchange) {

        return employeeService.updateEmployee(id, request, exchange);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<EmployeeResponse> getEmployee(@PathVariable @NotNull String id, ServerWebExchange exchange) {
        return employeeService.getEmployee(id, exchange);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable @NotNull String id) {
        return employeeService.removeEmployee(id);
    }
}
