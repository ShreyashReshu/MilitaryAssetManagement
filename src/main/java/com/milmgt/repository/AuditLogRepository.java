package com.milmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milmgt.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}