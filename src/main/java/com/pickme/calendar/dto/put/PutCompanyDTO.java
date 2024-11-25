package com.pickme.calendar.dto.put;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PutCompanyDTO {

    @Schema(example = "수정된 회사명")
    private String name;

    @Schema(example = "수정된 회사 위치")
    private String location;
}
