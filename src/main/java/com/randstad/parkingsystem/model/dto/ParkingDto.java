package com.randstad.parkingsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkingDto {
    private Long id;
    private Integer slotNumber;
    private String slotType;
    private String ownerName;
    private String contactNumber;
    private String vehicleNumber;
    private String parkInTime;
    private Date parkOutTime;
    private String availability;
}
