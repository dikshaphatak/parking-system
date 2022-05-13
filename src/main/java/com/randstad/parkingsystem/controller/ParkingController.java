package com.randstad.parkingsystem.controller;

import com.randstad.parkingsystem.model.dto.Parking;
import com.randstad.parkingsystem.model.dto.ParkingDto;
import com.randstad.parkingsystem.model.dto.ParkingHistoryDto;
import com.randstad.parkingsystem.model.dto.VehicleDto;
import com.randstad.parkingsystem.service.IParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {
    @Autowired
    private IParkingService parkingService;

    Logger logger = LoggerFactory.getLogger(ParkingController.class);

    @GetMapping("/list/details")
    public List<ParkingDto> getList(){
        logger.info("Received request to get all parked vehicle list");
        return parkingService.getParkingDetails();
    }

    @GetMapping("/parking/charges")
    public ResponseEntity<Double> getCharges(@RequestParam(value = "id") Long id){
        return new ResponseEntity<>(parkingService.parkoutCharges(id),HttpStatus.OK);
    }

    @PutMapping("/parkout")
    public ResponseEntity<Boolean> getParkingCharge(@RequestParam(value = "id") Long id, @RequestParam(value = "charges") Double charges){
        return new ResponseEntity<>(parkingService.parkoutVehicle(id, charges), HttpStatus.OK);
    }

    @GetMapping("/list/parkoutdetails")
    public List<ParkingHistoryDto> getParkoutVehicles(){
        logger.info("Received request to get all parked vehicle list");
        return parkingService.getParkedOutDetails();
    }

    @GetMapping("/vehicle/occupiedslot")
    public ResponseEntity<Boolean> checkOccupiedSlot(@RequestParam(value = "slotid") Long slotId){
        return new ResponseEntity<>(parkingService.checkOccupiedSlot(slotId), HttpStatus.OK);
    }
}
