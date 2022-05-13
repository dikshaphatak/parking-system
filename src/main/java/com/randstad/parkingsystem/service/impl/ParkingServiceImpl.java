package com.randstad.parkingsystem.service.impl;

import com.randstad.parkingsystem.dao.ParkingSlotRepository;
import com.randstad.parkingsystem.dao.VehicleRepository;
import com.randstad.parkingsystem.mapper.CustomObjectMapper;
import com.randstad.parkingsystem.model.ParkingSlot;
import com.randstad.parkingsystem.model.Vehicle;
import com.randstad.parkingsystem.model.dto.*;
import com.randstad.parkingsystem.model.enums.Availability;
import com.randstad.parkingsystem.service.IParkingService;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ParkingServiceImpl implements IParkingService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    CustomObjectMapper objectMapper;

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    @Value("${vehicle.type.two_wheeler}")
    String vehicleType;

    @Value("${price.vehicle.two_wheeler}")
    int twoWheelerPrice;

    @Value("${price.vehicle.four_wheeler}")
    int fourWheelerPrice;

    @Value("${parkintime.difference}")
    double zeroMinute;

    @Override
    public List<ParkingDto> getParkingDetails() {
            List<ParkingDto> parkingDtoList = new ArrayList<>();
            try{
                List<Parking> parkingList = vehicleRepository.getParkingDtos(Availability.OCCUPIED);
                parkingDtoList=objectMapper.toParkingDtoList(parkingList);
                for (ParkingDto parkingDto:parkingDtoList) {

                    if(StringUtils.equals(parkingDto.getSlotType(),vehicleType)){
                        parkingDto.setSlotType("Two-Wheeler");
                    }else {
                        parkingDto.setSlotType("Four-Wheeler");
                    }
                }

            }catch(Exception e){
                logger.error("Error occurred in getParkingDetails method",e);
            }
            return parkingDtoList;
        }


    @Override
    public List<ParkingHistoryDto> getParkedOutDetails() {
        List<ParkingHistoryDto> parkingHistoryDtoList = new ArrayList<>();
        try{
            parkingHistoryDtoList = vehicleRepository.getParkingHistoryDtos();
        }catch(Exception e){
            logger.error("Error occurred in getParkedOutDetails method",e);
        }

        return parkingHistoryDtoList;
    }

    @Override
    public Double parkoutCharges(Long id) {
        Double charges=0.0;
        Vehicle vehicle=null;
        ParkingSlot slot = null;
        VehicleDto vehicleDto=null;
        try{
            if(null!=id){
                vehicle = vehicleRepository.getVehicleById(id);
                if(null!=vehicle){
                    Date parkOutTime = DateTime.now().toDate();
                    vehicle.setParkOutTime(parkOutTime);
                    slot=vehicle.getSlot();
                     charges = calculateCharges(vehicle,slot.getSlotType().toString());

                    return charges;
                }
            }
        }
        catch(Exception e){
                logger.error("Error occurred in parkoutCharges method",e);
            }
        return charges;
    }

    /**
     * Check occupied slot
     *
     * @param slotId the slot id
     * @return boolean is occupied slot
     */
    @Override
    public Boolean checkOccupiedSlot(Long slotId) {
        boolean flag=false;
        ParkingSlot occupiedparkingSlot=new ParkingSlot();
        try{
          if(null!=slotId){
              occupiedparkingSlot=parkingSlotRepository.getParkingSlotByIdAndAvailability(slotId,Availability.OCCUPIED);
          }
          if(null!=occupiedparkingSlot){
              flag=true;
          }
        }catch (Exception e){
            logger.error("Error in checkOccupiedSlot method",e);
        }
        return flag;
    }

    @Override
    public Boolean parkoutVehicle(Long id, Double charges) {
        Vehicle vehicle=null;
        ParkingSlot slot = null;
        try{
            logger.info("Check for vehicle dto");
            if(null!=id) {
                logger.info("Check for vehicle inside db is present or not");
                vehicle = vehicleRepository.getVehicleById(id);
            }
            if(null!=vehicle){
                slot = vehicle.getSlot();
                vehicle.setParkOutTime(DateTime.now().toDate());
                vehicle.setParkingCharge(charges);
                slot.setAvailability(Availability.UNOCCUPIED);
                vehicle.setSlot(slot);
                vehicleRepository.save(vehicle);
                parkingSlotRepository.save(slot);
                return true;
            }
        }catch(Exception e){
            logger.error("Error occurred in parkoutVehicle method",e);
        }
        return false;
    }

        public Double calculateCharges(Vehicle vehicle, String slotType) {
            logger.info("inside calculate charges method");
            Date parkInTime = vehicle.getParkInTime();
            Date parkOutTime = vehicle.getParkOutTime();
            logger.info("Calculate  of hours the vehicle was parked");
            long diff = parkOutTime.getTime()-parkInTime.getTime();
            double totalPrice = Math.ceil((float)TimeUnit.MILLISECONDS.toMinutes(diff)/60);
            if(totalPrice==zeroMinute){
                if(StringUtils.equals(vehicleType, slotType)){
                    totalPrice = twoWheelerPrice;
                }else{
                    totalPrice = fourWheelerPrice;
                }
            }else{
                if(StringUtils.equals(vehicleType, slotType)){
                    totalPrice = totalPrice*twoWheelerPrice;
                }else {
                    totalPrice=totalPrice * fourWheelerPrice;
                }
            }

            return totalPrice;
        }

}

