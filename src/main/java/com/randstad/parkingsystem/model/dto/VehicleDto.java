package com.randstad.parkingsystem.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class VehicleDto {
    private Long id;
    @NotEmpty
    private String vehicleNumber;

    private String ownerName;
    @NotEmpty
    @Size(max = 10)
    private String contactNumber;

    @NotNull(message = "Cannot be null")
    private Integer slotId;
    private String parkInTime;
    private String parkOutTime ;
    private String parkingCharge;
}
