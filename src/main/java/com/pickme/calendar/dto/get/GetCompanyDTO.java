package com.pickme.calendar.dto.get;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetCompanyDTO {
    @Schema(description = "name", example = "KT")
    private String name;
    @Schema(description = "location", example = "경기도 성남시 정자동")
    private String location;
}
