package com.milmgt.service;

import com.milmgt.dto.AssetRequest;
import com.milmgt.dto.TransferRequest;
import com.milmgt.entity.*;
import com.milmgt.exception.ResourceNotFoundException;
import com.milmgt.exception.BadRequestException;
import com.milmgt.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final BaseRepository baseRepository;
    private final TransactionRepository transactionRepository;
    private final AuditLogRepository auditLogRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
    
    // NEW: Filter Logic
    public List<Asset> getAssetsByBase(Long baseId) {
        return assetRepository.findByCurrentBaseId(baseId);
    }

    public Asset addAsset(AssetRequest request) {
        Base base = baseRepository.findById(request.getBaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Base not found"));

        Asset asset = Asset.builder()
                .name(request.getName())
                .serialNumber(request.getSerialNumber())
                .type(request.getType())
                .status("ACTIVE")
                .currentBase(base)
                .build();
        return assetRepository.save(asset);
    }

    @Transactional
    public void transferAsset(TransferRequest request, String username) {
        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

        if (!asset.getCurrentBase().getId().equals(request.getSourceBaseId())) {
            throw new BadRequestException("Asset is not at source base!");
        }
        Base destBase = baseRepository.findById(request.getDestBaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination base invalid"));

        asset.setCurrentBase(destBase);
        assetRepository.save(asset);

        AssetTransaction transaction = AssetTransaction.builder()
                .asset(asset)
                .type("TRANSFER")
                .sourceBase(asset.getCurrentBase())
                .destBase(destBase)
                .timestamp(LocalDateTime.now())
                .initiatedBy(username)
                .build();
        transactionRepository.save(transaction);

        AuditLog log = AuditLog.builder()
                .action("TRANSFER_ASSET")
                .username(username)
                .timestamp(LocalDateTime.now())
                .details("Transferred " + asset.getSerialNumber() + " to " + destBase.getName())
                .build();
        auditLogRepository.save(log);
    }

    // NEW: Generic Status Updater (Assignments & Expenditures)
    public void updateStatus(Long assetId, String newStatus) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));
        asset.setStatus(newStatus);
        assetRepository.save(asset);
    }
}