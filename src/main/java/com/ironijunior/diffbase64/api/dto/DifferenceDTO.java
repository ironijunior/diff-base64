package com.ironijunior.diffbase64.api.dto;

import lombok.Data;

@Data
public class DifferenceDTO {

    private Long initialOffset;
    private Long finalOffset;
    private Long lenght;

}
