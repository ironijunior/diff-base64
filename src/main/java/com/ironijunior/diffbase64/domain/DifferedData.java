package com.ironijunior.diffbase64.domain;

import com.ironijunior.diffbase64.domain.enumerator.DiffStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents database entity of the diff.
 *
 * @author Ironi Junior Medina
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DifferedData implements Serializable {

	private static final long serialVersionUID = -1615025580764206382L;
	
	@Id
    @NonNull
    private String id;
    private String left;
    private String right;
    private String status = DiffStatus.NO_DIFF.getText();
    private List<DifferenceData> differences;
}
