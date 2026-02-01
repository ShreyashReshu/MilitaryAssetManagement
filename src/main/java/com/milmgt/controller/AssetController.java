package com.milmgt.controller;

import com.milmgt.entity.Asset;
import com.milmgt.entity.AuditLog;
import com.milmgt.entity.Base;
import com.milmgt.model.TransferLog;
import com.milmgt.service.AssetService;
import com.milmgt.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired private AssetService assetService;
    @Autowired private BaseRepository baseRepository;

    @GetMapping
    public List<Asset> getAllAssets() { return assetService.getAllAssets(); }

    @PostMapping
    public Asset addAsset(@RequestBody Asset asset) { return assetService.addAsset(asset); }

    @PostMapping("/transfer")
    public void transferAsset(@RequestBody Map<String, Long> payload) {
        assetService.transferAsset(payload.get("assetId"), payload.get("sourceBaseId"), payload.get("destBaseId"));
    }

    @PutMapping("/{id}/assign")
    public void assignAsset(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        assetService.assignAsset(id, payload.get("soldierName"));
    }

    @PutMapping("/{id}/expend")
    public void expendAsset(@PathVariable Long id) {
        assetService.expendAsset(id);
    }

    @GetMapping("/transfers/history")
    public List<TransferLog> getTransferHistory() { return assetService.getTransferHistory(); }

    @GetMapping("/audit-logs")
    public List<AuditLog> getAuditLogs() { return assetService.getAuditLogs(); }

    @GetMapping("/bases")
    public List<Base> getAllBases() { return baseRepository.findAll(); }
}