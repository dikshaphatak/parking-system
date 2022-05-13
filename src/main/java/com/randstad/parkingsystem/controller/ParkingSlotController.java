package com.randstad.parkingsystem.controller;

import com.randstad.parkingsystem.model.dto.SlotDto;

import com.randstad.parkingsystem.service.IParkingSlotService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/parking")
public class ParkingSlotController {

    Logger logger = LoggerFactory.getLogger(ParkingSlotController.class);

    @Autowired
    private IParkingSlotService parkingSlotService;


    @PostMapping("/slot/add")
    public ResponseEntity<SlotDto> createSlot(@Valid @RequestBody SlotDto slotDto){
        logger.info("Received request to create the new slot");
        SlotDto addedSlotDto = parkingSlotService.saveSlot(slotDto);
        return ResponseEntity.status(HttpStatus.OK).body(addedSlotDto);
    }

    @GetMapping("/slot/list")
    public List<SlotDto> getList(){
        logger.info("Received request for getting all slots from parking system");
        return parkingSlotService.getAllSlots();
    }

    @GetMapping("/slot/checkslot")
    public ResponseEntity<Boolean> checkSlot(@RequestParam(value = "slotNumber")Integer slotNumber){
        logger.info("Received request for checking  slot by Slot Number from parking system");
        return new ResponseEntity<>(parkingSlotService.checkSlot(slotNumber), HttpStatus.OK);
    }

    @GetMapping("/slot/get/{slotNumber}")
    public ResponseEntity<SlotDto> getSlot(@PathVariable("slotNumber")Integer slotNumber){
        logger.info("Received request for getting single slots by Slot Number from parking system");
        return new ResponseEntity<>(parkingSlotService.getSingleSlot(slotNumber), HttpStatus.OK);
    }

//    @PutMapping("/slot/update")
//    public ResponseEntity<SlotDto> updateSlot(@RequestBody SlotDto slotDto){
//        logger.info("Received request to update the slot");
//        return new ResponseEntity<>(parkingSlotService.saveSlot(slotDto),HttpStatus.OK);
//    }

    @DeleteMapping("/slot/delete/{slotNumber}")
    public ResponseEntity<Boolean> removeSlot(@PathVariable("slotNumber")Integer slotNumber){
        logger.info("Received request to delete the slot");
        return new ResponseEntity<>(parkingSlotService.deleteSlot(slotNumber),HttpStatus.OK);
    }


}
