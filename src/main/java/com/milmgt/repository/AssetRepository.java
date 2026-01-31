package com.milmgt.repository;

import com.milmgt.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    List<Asset> findByCurrentBaseId(Long baseId);

    long countByStatus(String status);
    
    Optional<Asset> findBySerialNumberIgnoreCase(String serialNumber);
}