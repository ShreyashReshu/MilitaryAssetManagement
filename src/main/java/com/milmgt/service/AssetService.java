package com.milmgt.service;

import com.milmgt.entity.Asset;
import com.milmgt.entity.Base;
import com.milmgt.entity.AuditLog; 
import com.milmgt.model.TransferLog;
import com.milmgt.repository.AssetRepository;
import com.milmgt.repository.BaseRepository;
import com.milmgt.repository.TransferLogRepository;
import com.milmgt.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    @Autowired private AssetRepository assetRepository;
    @Autowired private BaseRepository baseRepository;
    @Autowired private TransferLogRepository transferLogRepository;
    @Autowired private AuditLogRepository auditLogRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset addAsset(Asset asset) {
        if (asset.getCurrentBase() == null || asset.getCurrentBase().getId() == null) {
            throw new RuntimeException("Asset must be assigned to a Base (ID required)");
        }
        Base base = baseRepository.findById(asset.getCurrentBase().getId())
                .orElseThrow(() -> new RuntimeException("Base not found"));
        
        asset.setCurrentBase(base);
        asset.setStatus("ACTIVE");
        Asset saved = assetRepository.save(asset);

        // This line caused your error - it works now because File 1 is fixed
        auditLogRepository.save(new AuditLog("PURCHASE", saved.getName(), "Base: " + base.getName()));
        return saved;
    }

    public void transferAsset(Long assetId, Long sourceBaseId, Long destBaseId) {
        Asset asset = assetRepository.findById(assetId).orElseThrow(() -> new RuntimeException("Asset not found"));
        Base source = baseRepository.findById(sourceBaseId).orElseThrow(() -> new RuntimeException("Source Base not found"));
        Base dest = baseRepository.findById(destBaseId).orElseThrow(() -> new RuntimeException("Dest Base not found"));

        if (!asset.getCurrentBase().getId().equals(sourceBaseId)) {
            throw new RuntimeException("Asset is not at the source base");
        }

        asset.setCurrentBase(dest);
        assetRepository.save(asset);
        
        transferLogRepository.save(new TransferLog(asset.getName(), source.getName(), dest.getName()));
    }

    public void assignAsset(Long id, String soldierName) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
        asset.setStatus("ASSIGNED");
        assetRepository.save(asset);

        auditLogRepository.save(new AuditLog("ASSIGN", asset.getName(), "To: " + soldierName));
    }

    public void expendAsset(Long id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
        asset.setStatus("EXPENDED");
        assetRepository.save(asset);

        auditLogRepository.save(new AuditLog("EXPEND", asset.getName(), "Asset consumed/damaged"));
    }

    public List<TransferLog> getTransferHistory() {
        return transferLogRepository.findAllByOrderByTimestampDesc();
    }

    public List<AuditLog> getAuditLogs() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }
}