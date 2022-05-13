package com.randstad.parkingsystem.model.dto;

import com.randstad.parkingsystem.model.enums.Availability;
import com.randstad.parkingsystem.model.enums.SlotType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Parking {
    private Long id;
    private Integer slotNumber;
    private SlotType slotType;
    private String ownerName;
    private String contactNumber;
    private String vehicleNumber;
    private Date parkInTime;
    private Date parkOutTime;
    private Availability availability;
}
