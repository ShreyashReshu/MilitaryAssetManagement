package com.milmgt.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private Long assetId;
    private Long sourceBaseId;
    private Long destBaseId;
}