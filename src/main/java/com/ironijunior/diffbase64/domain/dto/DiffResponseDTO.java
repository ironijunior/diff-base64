package com.ironijunior.diffbase64.domain.dto;

import com.ironijunior.diffbase64.domain.entity.DifferedData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DiffResponseDTO {

    private String status;
    private List<DifferenceDTO> differences;

    public static DiffResponseDTO convertFromEntity(DifferedData diffData) {
        return DiffResponseDTO.builder()
                .status(diffData.getStatus())
                .differences(
                        diffData.getDifferences()
                                .stream()
                                .map(diff -> DifferenceDTO.builder()
                                        .initialOffset(diff.getInitialOffset())
                                        .finalOffset(diff.getFinalOffset())
                                        .lenght(diff.getLenght())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
