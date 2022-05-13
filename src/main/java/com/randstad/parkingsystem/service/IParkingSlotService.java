package com.randstad.parkingsystem.service;

import com.randstad.parkingsystem.model.dto.SlotDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.List;


public interface IParkingSlotService {


   List<SlotDto> getAllSlots();

    SlotDto saveSlot(SlotDto slotDto);

    SlotDto getSingleSlot(Integer slotNumber);
   Boolean deleteSlot(Integer slotNumber);

   List<SlotDto> getUnoccupiedSlots();

    Boolean checkSlot(Integer slotNumber);
}
