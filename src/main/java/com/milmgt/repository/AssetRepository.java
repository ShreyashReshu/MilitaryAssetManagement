package com.milmgt.repository;

import com.milmgt.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    // Filters for "View Assets"
    List<Asset> findByCurrentBaseId(Long baseId);

    // Dashboard Math
    long countByStatus(String status);
}