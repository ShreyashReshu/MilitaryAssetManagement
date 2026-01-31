package com.milmgt.service;

import com.milmgt.entity.Asset;
import com.milmgt.repository.AssetRepository;
import com.milmgt.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {

    @Mock private AssetRepository assetRepository;
    @Mock private AuditLogRepository auditLogRepository;
    @InjectMocks private AssetService assetService;

    @Test
    public void testAssignAsset_Success() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setStatus("ACTIVE");
        asset.setName("M4 Rifle");

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));

        assetService.assignAsset(1L, "Soldier Ryan");

        assertEquals("ASSIGNED", asset.getStatus());
        verify(assetRepository, times(1)).save(asset); // Verify DB save called
    }

    @Test
    public void testAssignAsset_Fail_IfExpended() {
        Asset asset = new Asset();
        asset.setId(2L);
        asset.setStatus("EXPENDED"); // Asset is dead

        when(assetRepository.findById(2L)).thenReturn(Optional.of(asset));

        // Expect Exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            assetService.assignAsset(2L, "Soldier Ryan");
        });

        assertTrue(exception.getMessage().contains("Only ACTIVE assets can be assigned"));
    }
}