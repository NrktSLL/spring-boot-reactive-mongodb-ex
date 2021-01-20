package com.nrkt.springbootreactivemongodb.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;

@Document("employee")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements Serializable {
    @Id
    String id;
    @NotBlank
    String firstName;
    String secondName;
    @NotBlank
    String lastName;
    @Email
    String email;
    @PastOrPresent
    Date hiredDate;
}
