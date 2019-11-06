package com.ironijunior.diffbase64.transport.repository;

import com.ironijunior.diffbase64.domain.DifferedData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffRepository extends CrudRepository<DifferedData, String> {
}
