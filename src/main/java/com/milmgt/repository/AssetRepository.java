package com.milmgt.repository;

import com.milmgt.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    List<Asset> findByCurrentBaseId(Long baseId);

    long countByStatus(String status);
    
    @Query("SELECT a FROM Asset a WHERE LOWER(TRIM(a.serialNumber)) = LOWER(TRIM(:serialNumber))")
    Optional<Asset> findBySerialNumberIgnoreCase(@Param("serialNumber") String serialNumber);
}