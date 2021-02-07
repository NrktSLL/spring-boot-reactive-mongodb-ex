package com.nrkt.springbootreactivemongodb.service.employee;

import com.nrkt.springbootreactivemongodb.domain.Employee;
import com.nrkt.springbootreactivemongodb.dto.request.EmployeeRequest;
import com.nrkt.springbootreactivemongodb.dto.response.EmployeeResponse;
import com.nrkt.springbootreactivemongodb.mapper.EmployeeMapper;
import com.nrkt.springbootreactivemongodb.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplTest {

    private static final String Firstname = "DEFAULT_FIRSTNAME";
    private static final String Lastname = "DEFAULT_LASTNAME";
    private static final String Secondname = "DEFAULT_SECONDNAME";
    private static final String Email = "aa@gmail.com";

    private EmployeeRequest employeeRequest;
    private EmployeeResponse employeeResponse;
    private Employee employee;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

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

        employeeResponse = EmployeeResponse.builder()
                .firstName(employee.getFirstName())
                .secondName(employee.getSecondName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
        employeeResponse.setId(employee.getId());

        when(employeeMapper.employeeRequestToEmployeeEntity(ArgumentMatchers.any(EmployeeRequest.class))).thenReturn(employee);
        when(employeeMapper.employeeEntityToEmployeeResponse(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(employeeResponse);
    }

    @Test
    void addEmployeeTest() {
        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(Mono.just(employee));

        Optional<EmployeeResponse> response =
                employeeService.addEmployee(employeeRequest, null).blockOptional();

        assertThat(response).isPresent();
        assertThat(response.orElse(null).getFirstName()).isNotNull().isEqualTo(employee.getFirstName());
        assertThat(response.orElse(null).getSecondName()).isNotNull().isEqualTo(employee.getSecondName());
        assertThat(response.orElse(null).getLastName()).isNotNull().isEqualTo(employee.getLastName());
        assertThat(response.orElse(null).getEmail()).isNotNull().isEqualTo(employee.getEmail());
        assertThat(response.orElse(null).getId()).isNotEmpty();
    }

    @Test
    void updateEmployeeTest() {
        EmployeeRequest employeeRequestUpdateTest = new EmployeeRequest();
        employeeRequestUpdateTest.setFirstName("Nrkt");
        employeeRequestUpdateTest.setLastName("SLL");
        employeeRequestUpdateTest.setEmail("aa@gmail.com");

        when(employeeRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Mono.just(employee));
        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(Mono.just(employee));

        EmployeeResponse response = employeeService.updateEmployee(employee.getId(),
                employeeRequestUpdateTest,
                null).block();

        response.setId(UUID.randomUUID().toString());

        assertThat(response.getFirstName()).isNotNull();
        assertThat(response.getSecondName()).isNotNull();
        assertThat(response.getLastName()).isNotNull();
        assertThat(response.getEmail()).isNotNull();
        assertThat(response.getId()).isNotEqualTo(employee.getId());
    }

    @Test
    void removeEmployeeTest() {
        employeeService.removeEmployee(employee.getId());

        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }

    @Test
    void getEmployeeTest() {
        when(employeeRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Mono.just(employee));

        Optional<EmployeeResponse> response =
                employeeService.getEmployee(employee.getId(), null).blockOptional();

        assertThat(response).isPresent();
        assertThat(response.orElse(null).getSecondName()).isNotNull();
        assertThat(response.orElse(null).getFirstName()).isNotNull();
        assertThat(response.orElse(null).getLastName()).isNotNull();
        assertThat(response.orElse(null).getEmail()).isNotNull();
        assertThat(response.orElse(null).getId()).isNotEmpty();
    }
}