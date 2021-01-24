package com.nrkt.springbootreactivemongodb.mapper;

import com.nrkt.springbootreactivemongodb.decorator.EmployeeDecorator;
import com.nrkt.springbootreactivemongodb.domain.Employee;
import com.nrkt.springbootreactivemongodb.dto.request.EmployeeRequest;
import com.nrkt.springbootreactivemongodb.dto.response.EmployeeResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.server.ServerWebExchange;

@Mapper
@DecoratedWith(EmployeeDecorator.class)
public interface EmployeeMapper {

    EmployeeResponse employeeEntityToEmployeeResponse(Employee employee, ServerWebExchange exchange);

    @Mapping(target = "hiredDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    Employee employeeRequestToEmployeeEntity(EmployeeRequest employeeRequest);
}
