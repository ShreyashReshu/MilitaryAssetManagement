package com.milmgt.dto;

import lombok.Data;

@Data
public class AssetRequest {
    private String name;
    private String serialNumber;
    private String type;
    private Long baseId;
}