package com.ironijunior.diffbase64.transport.repository;

import com.ironijunior.diffbase64.domain.DifferedData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class that represents the persistent methods for the {@link DifferedData} document.
 *
 * @author Ironi Junior Medina
 */
@Repository
public interface DiffRepository extends CrudRepository<DifferedData, String> {
}
