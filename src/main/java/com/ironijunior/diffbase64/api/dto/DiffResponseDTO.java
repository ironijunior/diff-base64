package com.ironijunior.diffbase64.api.dto;

import com.ironijunior.diffbase64.api.dto.enumerator.DiffStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class DiffResponseDTO {

    private DiffStatus result;
    private List<DifferenceDTO> differences;

}
