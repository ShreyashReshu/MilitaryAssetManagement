package com.milmgt.service;

import com.milmgt.entity.Asset;
import com.milmgt.entity.Base;
import com.milmgt.model.TransferLog;
import com.milmgt.repository.AssetRepository;
import com.milmgt.repository.BaseRepository;
import com.milmgt.repository.TransferLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private TransferLogRepository transferLogRepository;

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
        return assetRepository.save(asset);
    }

    public void transferAsset(Long assetId, Long sourceBaseId, Long destBaseId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
        Base source = baseRepository.findById(sourceBaseId)
                .orElseThrow(() -> new RuntimeException("Source Base not found"));
        Base dest = baseRepository.findById(destBaseId)
                .orElseThrow(() -> new RuntimeException("Dest Base not found"));

        if (!asset.getCurrentBase().getId().equals(sourceBaseId)) {
            throw new RuntimeException("Asset is not at the source base");
        }

        // 1. Move Asset
        asset.setCurrentBase(dest);
        assetRepository.save(asset);

        // 2. Log History
        TransferLog log = new TransferLog(asset.getName(), source.getName(), dest.getName());
        transferLogRepository.save(log);
    }

    public void assignAsset(Long id, String soldierName) {
        Asset asset = assetRepository.findById(id).orElseThrow();
        asset.setStatus("ASSIGNED");
        assetRepository.save(asset);
    }

    public void expendAsset(Long id) {
        Asset asset = assetRepository.findById(id).orElseThrow();
        asset.setStatus("EXPENDED");
        assetRepository.save(asset);
    }

    public List<TransferLog> getTransferHistory() {
        return transferLogRepository.findAllByOrderByTimestampDesc();
    }
}