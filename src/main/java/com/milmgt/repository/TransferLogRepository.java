package com.milmgt.repository;

import com.milmgt.model.TransferLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransferLogRepository extends JpaRepository<TransferLog, Long> {
    List<TransferLog> findAllByOrderByTimestampDesc();
}