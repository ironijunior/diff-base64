package com.ironijunior.diffbase64.transport.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Class that represent the {@link com.ironijunior.diffbase64.domain.DifferenceData}
 * and it is exposed via REST api.
 *
 * @author Ironi Junior Medina
 */
@Data
@Builder
public class DifferenceDTO {

    private Long initialOffset;
    private Long finalOffset;
    private Long length;

}
