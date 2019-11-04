package com.ironijunior.diffbase64.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DifferenceData {

    private Long initialOffset;
    private Long finalOffset;
    private Long lenght;
}
