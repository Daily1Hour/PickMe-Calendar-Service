package com.pickme.calendar.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PostCompanyDTO {

    @Schema(example = "앙떼띠")
    private String name;

    @Schema(example = "서울특별시 송파구 방이동 번지 1호 23-2 2층")
    private String location;
}
