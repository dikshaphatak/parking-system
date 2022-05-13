package com.randstad.parkingsystem.controller;

import com.randstad.parkingsystem.model.dto.Parking;
import com.randstad.parkingsystem.model.dto.ParkingDto;
import com.randstad.parkingsystem.model.dto.VehicleDto;
import com.randstad.parkingsystem.service.IVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/parking")
public class VehicleController {

    @Autowired
    private IVehicleService vehicleService;

    Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @PostMapping("/vehicle/add")
    public ResponseEntity<ParkingDto> addVehicle(@Valid @RequestBody VehicleDto dto){
        logger.info("Received request to add the new vehicle ");
        ParkingDto addedVehicleDto = vehicleService.saveVehicle(dto);
        return ResponseEntity.status(HttpStatus.OK).body(addedVehicleDto);
    }

    @PutMapping("/vehicle/update")
    public ResponseEntity<ParkingDto> updateVehicle(@RequestBody VehicleDto vehicleDto){
        logger.info("Received request to update the vehicle");
        return new ResponseEntity<>(vehicleService.saveVehicle(vehicleDto),HttpStatus.OK);
    }

    @GetMapping("/vehicle/all")
    public List<VehicleDto> getAllVehicle(){
        logger.info("Received request to get all vehicle");
        return vehicleService.getAllVehicle();
    }

    @GetMapping("/vehicle/get/{id}")
    public ResponseEntity<VehicleDto> getSlot(@PathVariable("id")Long id){
        logger.info("Received request for getting single vehicle data ");
        return new ResponseEntity<>(vehicleService.getSingleVehicle(id), HttpStatus.OK);
    }

    @DeleteMapping("/vehicle/delete/{id}")
    public ResponseEntity<Boolean> removeVehicle(@PathVariable("id") Long id){
        return new ResponseEntity<>(vehicleService.deleteVehicle(id), HttpStatus.OK);
    }

}
