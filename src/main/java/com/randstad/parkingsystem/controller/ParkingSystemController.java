package com.randstad.parkingsystem.controller;

import com.randstad.parkingsystem.model.dto.Parking;
import com.randstad.parkingsystem.model.dto.ParkingDto;
import com.randstad.parkingsystem.model.dto.ParkingHistoryDto;
import com.randstad.parkingsystem.model.dto.SlotDto;
import com.randstad.parkingsystem.service.IParkingService;
import com.randstad.parkingsystem.service.IParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/parkingsystem")
public class ParkingSystemController {

    @Autowired
    private IParkingSlotService parkingSlotService;

    @Autowired
    private IParkingService parkingService;

    @GetMapping("/dashboard/view")
    public ModelAndView showDashboard(){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        return modelAndView;
    }
    @GetMapping("/dashboard/slot")
    public ModelAndView showSlot(){
        ModelAndView modelAndView = new ModelAndView("slot");
        List<SlotDto> slotDtoList= parkingSlotService.getAllSlots();
        modelAndView.addObject("slots",slotDtoList);
        return modelAndView;
    }

    @GetMapping("/dashboard/parking")
    public ModelAndView showParking(){
        ModelAndView modelAndView = new ModelAndView("parking");
        List<ParkingDto> parkingDtoList = parkingService.getParkingDetails();
        List<SlotDto> slotDtoList = parkingSlotService.getUnoccupiedSlots();
        modelAndView.addObject("parkings",parkingDtoList);
        modelAndView.addObject("slots",slotDtoList);
        return modelAndView;
    }

    @GetMapping("/dashboard/history")
    public ModelAndView showHistory(){
        ModelAndView modelAndView = new ModelAndView("parkinghistory");
        List<ParkingHistoryDto> parkingHistoryDtoList = parkingService.getParkedOutDetails();
        modelAndView.addObject("histories",parkingHistoryDtoList);
        return modelAndView;
    }

    @GetMapping("/dashboard/newslot")
    public ModelAndView getAddSlotPage(){
        ModelAndView modelAndView = new ModelAndView("newslot");
        return modelAndView;
    }
    @GetMapping("/dashboard/newparking")
    public ModelAndView getAddParkingPage(){
        ModelAndView modelAndView = new ModelAndView("newparking");
        return modelAndView;
    }
}
