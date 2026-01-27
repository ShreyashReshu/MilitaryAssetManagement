package com.milmgt.controller;

import com.milmgt.dto.AssetRequest;
import com.milmgt.dto.TransferRequest;
import com.milmgt.entity.Asset;
import com.milmgt.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    // 1. View Assets (Filtered) - Open to everyone authenticated
    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets(@RequestParam(required = false) Long baseId) {
        if (baseId != null) return ResponseEntity.ok(assetService.getAssetsByBase(baseId));
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    // 2. Add Asset - ADMIN ONLY
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Asset> addAsset(@RequestBody AssetRequest request) {
        return ResponseEntity.ok(assetService.addAsset(request));
    }

    // 3. Transfer - ADMIN & LOGISTICS
    @PostMapping("/transfer")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICS')")
    public ResponseEntity<String> transferAsset(@RequestBody TransferRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        assetService.transferAsset(request, username);
        return ResponseEntity.ok("Transfer successful");
    }

    // 4. Assign to Soldier - COMMANDER ONLY
    @PutMapping("/{id}/assign")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMMANDER')")
    public ResponseEntity<String> assignAsset(@PathVariable Long id) {
        assetService.updateStatus(id, "ASSIGNED");
        return ResponseEntity.ok("Asset Assigned to Personnel");
    }

    // 5. Expend Asset - COMMANDER ONLY
    @PutMapping("/{id}/expend")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMMANDER')")
    public ResponseEntity<String> expendAsset(@PathVariable Long id) {
        assetService.updateStatus(id, "EXPENDED");
        return ResponseEntity.ok("Asset Marked as Expended");
    }
}