package com.ironijunior.diffbase64.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DifferenceData {

    private Long initialOffset;
    private Long finalOffset;
    private Long length;
}
