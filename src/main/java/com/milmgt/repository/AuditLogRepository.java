package com.milmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.milmgt.entity.AuditLog;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllByOrderByTimestampDesc();
}