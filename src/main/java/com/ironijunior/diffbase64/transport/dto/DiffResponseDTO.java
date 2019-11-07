package com.ironijunior.diffbase64.transport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ironijunior.diffbase64.domain.DifferedData;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represent the success response for the REST api.
 *
 * @author Ironi Junior Medina
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiffResponseDTO {

    private String status;
    private List<DifferenceDTO> differences;

    /**
     * Method responsible for converting the domain class {@link DifferedData}
     * into an success response to be exposed.
     *
     * @param diffData {@link DifferedData}
     * @return {@link DiffResponseDTO} to be exposed
     */
    public static DiffResponseDTO convertFromEntity(DifferedData diffData) {
        DiffResponseDTO.DiffResponseDTOBuilder builder = DiffResponseDTO.builder()
                .status(diffData.getStatus());

        if (!CollectionUtils.isEmpty(diffData.getDifferences())) {
            builder.differences(convertDifferences(diffData));
        }
        return builder.build();
    }

    private static List<DifferenceDTO> convertDifferences(DifferedData diffData) {
        return diffData.getDifferences()
                .stream()
                .map(DifferenceDTO::convertFromEntity)
                .collect(Collectors.toList());
    }
}
