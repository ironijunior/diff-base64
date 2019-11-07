package com.ironijunior.diffbase64.transport.dto;

import com.ironijunior.diffbase64.domain.DifferenceData;
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

    /**
     * Method responsible for converting the domain class {@link DifferenceData}
     * into an DTO that could be exposed.
     *
     * @param differenceData {@link DifferenceData}
     * @return {@link DifferenceDTO} to be exposed
     */
    public static DifferenceDTO convertFromEntity(DifferenceData differenceData) {
        return DifferenceDTO.builder()
                .initialOffset(differenceData.getInitialOffset())
                .finalOffset(differenceData.getFinalOffset())
                .length(differenceData.getLength())
                .build();
    }
}
