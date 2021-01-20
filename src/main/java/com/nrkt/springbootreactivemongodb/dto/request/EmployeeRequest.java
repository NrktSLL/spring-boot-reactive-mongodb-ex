package com.nrkt.springbootreactivemongodb.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {

    @NotBlank
    @Schema(description = "Employee first name", example = "ali", required = true)
    String firstName;

    @Schema(description = "Employee second name", example = "nrkt", required = true)
    String secondName;

    @NotBlank
    @Schema(description = "Employee last name", example = "sll", required = true)
    String lastName;

    @Email
    @Schema(description = "Employee email", example = "aa@bb.com", required = true)
    String email;
}
