package com.nrkt.springbootreactivemongodb.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nrkt.springbootreactivemongodb.dto.response.base.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Relation(collectionRelation = "employees", itemRelation = "employee")
@Builder
public class EmployeeResponse extends BaseResponse {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
    @PastOrPresent
    Date hiredDate;
}
