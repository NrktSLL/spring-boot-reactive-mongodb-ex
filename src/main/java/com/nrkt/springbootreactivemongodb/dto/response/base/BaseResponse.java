package com.nrkt.springbootreactivemongodb.dto.response.base;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BaseResponse extends RepresentationModel<BaseResponse> implements Serializable {
    @EqualsAndHashCode.Include
    String id;
}
