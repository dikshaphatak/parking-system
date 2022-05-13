package com.randstad.parkingsystem.service;

import com.randstad.parkingsystem.model.dto.Parking;
import com.randstad.parkingsystem.model.dto.ParkingDto;
import com.randstad.parkingsystem.model.dto.ParkingHistoryDto;
import com.randstad.parkingsystem.model.dto.VehicleDto;

import java.util.List;

public interface IParkingService {
    List<ParkingDto> getParkingDetails();
    Boolean parkoutVehicle(Long id, Double charges);

    List<ParkingHistoryDto> getParkedOutDetails();

    Double parkoutCharges(Long id);

    Boolean checkOccupiedSlot(Long slotId);
}
