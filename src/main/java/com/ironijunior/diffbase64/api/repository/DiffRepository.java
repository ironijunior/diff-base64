package com.ironijunior.diffbase64.api.repository;

import com.ironijunior.diffbase64.domain.DifferedData;
import org.springframework.data.repository.CrudRepository;

public interface DiffRepository extends CrudRepository<DifferedData, String> {
}
