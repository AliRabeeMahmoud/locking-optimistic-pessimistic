package com.example.locking.repository;


import com.example.locking.entity.BusDetails;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BusRepository extends CrudRepository<BusDetails, Long> {

//	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<BusDetails> findWithLockingById(Long id);
}
