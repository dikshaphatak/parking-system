package com.randstad.parkingsystem.mapper;

import com.randstad.parkingsystem.model.dto.SlotDto;
import com.randstad.parkingsystem.model.ParkingSlot;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Converter {

    @Autowired
    ModelMapper modelMapper;


    public SlotDto entityToDto(ParkingSlot parkingSlot){
        SlotDto slotsDTO = new SlotDto();
        slotsDTO = modelMapper.map(parkingSlot, SlotDto.class);
        return slotsDTO;
    }

    public  ParkingSlot dtoToEntity(SlotDto slotsDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        ParkingSlot parkingSlot= modelMapper.map(slotsDTO, ParkingSlot.class);
        return parkingSlot;

    }
}
