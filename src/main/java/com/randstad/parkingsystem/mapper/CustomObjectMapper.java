package com.randstad.parkingsystem.mapper;

import com.randstad.parkingsystem.model.ParkingSlot;
import com.randstad.parkingsystem.model.Vehicle;
import com.randstad.parkingsystem.model.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomObjectMapper {//
    SlotDto toSlotDto(ParkingSlot parkingSlot);

    ParkingSlot toParkingSlot(SlotDto slotDto, @MappingTarget ParkingSlot parkingSlot);
    //method for list

    List<SlotDto> toSlotDtoList(List<ParkingSlot> parkingSlotList);


    @Mapping(target = "slotId", expression = "java(vehicle.getSlot().getSlotNumber())")
    @Mapping(target = "parkInTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "parkOutTime", dateFormat = "yyyy-MM-dd HH:mm:ss")//
    public VehicleDto toVehicleDto(Vehicle vehicle);

    @Mapping(target = "slot", ignore = true)
    @Mapping(target = "parkInTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "parkOutTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public Vehicle toVehicle (VehicleDto vehicleDto, @MappingTarget Vehicle vehicle);

    public List<VehicleDto> toVehicleDtoList(List<Vehicle> vehicleList);

    @Mapping(target = "slotNumber", expression = "java(vehicle.getSlot().getSlotNumber())")
    ParkingHistoryDto toParkingHistoryDto(Vehicle vehicle);

    @Mapping(target = "parkInTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ParkingDto parkingToParkingDto(Parking parking);

    List<ParkingDto> toParkingDtoList(List<Parking> parkingList);
}
