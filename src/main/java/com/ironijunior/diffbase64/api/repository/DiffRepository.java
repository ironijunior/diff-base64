package com.ironijunior.diffbase64.api.repository;

import com.ironijunior.diffbase64.domain.entity.DifferedData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffRepository extends CrudRepository<DifferedData, String> {
}
