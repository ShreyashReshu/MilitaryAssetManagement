package com.milmgt.controller;

import com.milmgt.repository.AssetRepository;
import com.milmgt.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final AssetRepository assetRepo;
    private final TransactionRepository transRepo;

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        metrics.put("totalAssets", assetRepo.count());
        metrics.put("activeAssets", assetRepo.countByStatus("ACTIVE"));
        metrics.put("assignedAssets", assetRepo.countByStatus("ASSIGNED"));
        metrics.put("expendedAssets", assetRepo.countByStatus("EXPENDED"));

        long purchases = transRepo.findAll().stream().filter(t -> "PURCHASE".equals(t.getType())).count();
        long transfers = transRepo.findAll().stream().filter(t -> "TRANSFER".equals(t.getType())).count();

        Map<String, Long> movements = new HashMap<>();
        movements.put("purchases", purchases);
        movements.put("transfers", transfers);
        movements.put("netMovement", purchases + transfers);
        
        metrics.put("movements", movements);

        return ResponseEntity.ok(metrics);
    }
}