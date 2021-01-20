package com.nrkt.springbootreactivemongodb.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nrkt.springbootreactivemongodb.dto.response.base.BaseResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileResponse extends BaseResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
    Date uploadDate;
}
