package com.milmgt.repository;

import com.milmgt.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    List<Asset> findByCurrentBaseId(Long baseId);

    long countByStatus(String status);
}