package com.ironijunior.diffbase64.transport.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DifferenceDTO {

    private Long initialOffset;
    private Long finalOffset;
    private Long length;

}
