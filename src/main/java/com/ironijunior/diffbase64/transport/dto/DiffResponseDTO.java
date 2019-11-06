package com.ironijunior.diffbase64.transport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ironijunior.diffbase64.domain.DifferedData;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiffResponseDTO {

    private String status;
    private List<DifferenceDTO> differences;

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
                .map(diff -> DifferenceDTO.builder()
                        .initialOffset(diff.getInitialOffset())
                        .finalOffset(diff.getFinalOffset())
                        .length(diff.getLength())
                        .build())
                .collect(Collectors.toList());
    }
}
