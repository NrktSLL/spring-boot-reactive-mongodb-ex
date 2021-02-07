package com.nrkt.springbootreactivemongodb.api;

import com.nrkt.springbootreactivemongodb.domain.Employee;
import com.nrkt.springbootreactivemongodb.dto.request.EmployeeRequest;
import com.nrkt.springbootreactivemongodb.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@WebFluxTest(EmployeeController.class)
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    WebTestClient webTestClient;

    private static final String Firstname = "DEFAULT_FIRSTNAME";
    private static final String Lastname = "DEFAULT_LASTNAME";
    private static final String Secondname = "DEFAULT_SECONDNAME";
    private static final String Email = "aa@gmail.com";

    private EmployeeRequest employeeRequest;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeRequest = EmployeeRequest.builder()
                .email(Email)
                .firstName(Firstname)
                .lastName(Lastname)
                .secondName(Secondname).build();

        employee = Employee.builder()
                .id(UUID.randomUUID().toString())
                .firstName(employeeRequest.getFirstName())
                .secondName(employeeRequest.getSecondName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .build();
    }

    @Test
    void getEmployeeListTest() {
        webTestClient
                .get()
                .uri("/v1/employees")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void createEmployee() {
        webTestClient
                .post()
                .uri("/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeRequest), EmployeeRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody();
    }

    @Test
    void editEmployee() {
        webTestClient
                .put()
                .uri("/v1/employees/" + employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeRequest), EmployeeRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    void getEmployee() {
        webTestClient
                .get()
                .uri("/v1/employees/" + employee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    void deleteEmployee() {
        webTestClient
                .delete()
                .uri("/v1/employees/" + employee.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
}