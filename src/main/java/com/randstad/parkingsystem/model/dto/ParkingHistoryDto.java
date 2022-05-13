package com.randstad.parkingsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParkingHistoryDto {
    private Integer slotNumber;
    private String vehicleNumber;
    private String ownerName;
    private String contactNumber;
    private Date parkInTime;
    private Date parkOutTime;
    private Double parkingCharge;
}
