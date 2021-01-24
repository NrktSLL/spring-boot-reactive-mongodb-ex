package com.nrkt.springbootreactivemongodb.decorator;

import com.nrkt.springbootreactivemongodb.api.EmployeeController;
import com.nrkt.springbootreactivemongodb.domain.Employee;
import com.nrkt.springbootreactivemongodb.dto.response.EmployeeResponse;
import com.nrkt.springbootreactivemongodb.mapper.EmployeeMapper;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

public abstract class EmployeeDecorator implements EmployeeMapper {

    @Override
    public EmployeeResponse employeeEntityToEmployeeResponse(Employee employee, ServerWebExchange exchange) {
        var response = new EmployeeResponse();
        response.setEmail(employee.getEmail());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setHiredDate(employee.getHiredDate());
        response.setId(employee.getId());

        var mediaType = exchange.getRequest().getQueryParams().getFirst("mediaType");

        if (Objects.requireNonNull(mediaType).equals("hal")) {
            WebFluxLink[] links = new WebFluxLink[]{
                    linkTo(methodOn(EmployeeController.class).createEmployee(null, null))
                            .withRel("employee").map(x -> x.withDeprecation("Add Employee")),
                    linkTo(methodOn(EmployeeController.class).editEmployee(employee.getId(), null, null))
                            .withRel("employee").map(x -> x.withDeprecation("Edit Employee")),
                    linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId(), null))
                            .withRel("employee").map(x -> x.withType("Get Employee")),
                    linkTo(methodOn(EmployeeController.class).getEmployeeList(null))
                            .withRel("employee").map(x -> x.withDeprecation("Get All Employee"))
            };

            Arrays.stream(links).forEach(link -> link.toMono().map(response::add).subscribe());
        }
        return response;
    }
}