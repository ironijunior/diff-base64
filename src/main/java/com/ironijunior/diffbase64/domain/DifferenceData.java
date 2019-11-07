package com.ironijunior.diffbase64.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents database entity of the differences.
 *
 * @author Ironi Junior Medina
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DifferenceData {

    private Long initialOffset;
    private Long finalOffset;
    private Long length;
}
