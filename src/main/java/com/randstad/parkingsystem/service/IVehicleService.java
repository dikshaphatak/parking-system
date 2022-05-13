package com.randstad.parkingsystem.service;

import com.randstad.parkingsystem.model.dto.Parking;
import com.randstad.parkingsystem.model.dto.ParkingDto;
import com.randstad.parkingsystem.model.dto.VehicleDto;

import java.util.List;

public interface IVehicleService {

     ParkingDto saveVehicle(VehicleDto vehicleDto);
     VehicleDto getSingleVehicle(Long id);
     List<VehicleDto> getAllVehicle();
     Boolean deleteVehicle(Long id);



}
