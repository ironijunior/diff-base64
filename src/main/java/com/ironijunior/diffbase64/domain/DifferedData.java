package com.ironijunior.diffbase64.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DifferedData {

    private String id;
    private String left;
    private String right;
    private List<DifferenceData> differences;
}
