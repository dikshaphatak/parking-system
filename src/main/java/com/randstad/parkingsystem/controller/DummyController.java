package com.randstad.parkingsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

//    @Autowired
//    ParkingSlotService parkingSlotService;

    @GetMapping("/login")
    public String login(){
        return "Hello Admin!!!";
    }

//    @PostMapping("/addslot")
//    public String addSlot(@RequestBody SlotsDTO slotsDTO){
//        String parkingSlot = parkingSlotService.createSlot(slotsDTO);
//        return parkingSlot;
//
//    }

}
