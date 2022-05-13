package com.randstad.parkingsystem.service.impl;

import com.randstad.parkingsystem.dao.ParkingSlotRepository;
import com.randstad.parkingsystem.dao.VehicleRepository;
import com.randstad.parkingsystem.exception.ServiceException;
import com.randstad.parkingsystem.mapper.CustomObjectMapper;
import com.randstad.parkingsystem.model.ParkingSlot;
import com.randstad.parkingsystem.model.Vehicle;
import com.randstad.parkingsystem.model.dto.ParkingDto;
import com.randstad.parkingsystem.model.dto.SlotDto;
import com.randstad.parkingsystem.model.dto.VehicleDto;
import com.randstad.parkingsystem.model.enums.Availability;
import com.randstad.parkingsystem.service.IVehicleService;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VehicleServiceImpl implements IVehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    CustomObjectMapper customObjectMapper;

    Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Value("${vehicle.type.two_wheeler}")
    String vehicleType;

    @Override
    @Transactional
    public ParkingDto saveVehicle(VehicleDto vehicleDto) {
        logger.info("Inside saveVehicle method");
        Vehicle vehicle=null;
        ParkingSlot slot=null;
        ParkingDto parkingDto =new ParkingDto();
        SlotDto slotDto =null;
        try{
            if(null!=vehicleDto){
                logger.info("Vehicle Dto is not Null");
                if(null==vehicleDto.getId()){
                    vehicle = new Vehicle();
                    vehicle.setActiveFlag(Boolean.TRUE);
                    vehicle.setCreated(DateTime.now().toDate());
                }
                else{
                    vehicle = vehicleRepository.getVehicleById(vehicleDto.getId());
                }
                logger.info("Converting vehicleDto to vehicle");
                vehicle = customObjectMapper.toVehicle(vehicleDto,vehicle);
                slot = parkingSlotRepository.getParkingSlotById(Long.valueOf(vehicleDto.getSlotId()));
                if(null!=slot && null==vehicle.getParkOutTime() ){
                    slot.setAvailability(Availability.OCCUPIED);
                }else{
                    slot.setAvailability(Availability.UNOCCUPIED);
                }
                vehicle.setSlot(slot);
                logger.info("Saving vehicle in db");
                vehicle=vehicleRepository.save(vehicle);
                //vehicleDto.setSlotId(slot.getSlotNumber());
                logger.info("Vehicle obj saved in db");
                vehicleDto=customObjectMapper.toVehicleDto(vehicle);
                parkingDto.setId(vehicleDto.getId());
                parkingDto.setSlotNumber(slot.getSlotNumber());
                parkingDto.setOwnerName(vehicleDto.getOwnerName());
                parkingDto.setContactNumber(vehicleDto.getContactNumber());
                parkingDto.setVehicleNumber(vehicleDto.getVehicleNumber());
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String parkintime = simpleDateFormat.format(vehicle.getParkInTime());
                parkingDto.setParkInTime(vehicleDto.getParkInTime());
                parkingDto.setAvailability(slot.getAvailability().toString());
                if(StringUtils.equals(slot.getSlotType().toString(),vehicleType)){
                    parkingDto.setSlotType("Two-Wheeler");
                }else {
                    parkingDto.setSlotType("Four-Wheeler");
                }
                logger.info("Converted vehicle into vehicle dto");
                return parkingDto;
            }
        }catch (Exception exception){
            logger.error("An error occurred in save method on Vehicle", exception);
            throw new ServiceException("Encountered null value");
        }
        return parkingDto;
    }

    @Override
    public VehicleDto getSingleVehicle(Long id) {
        Vehicle vehicle= new Vehicle();
        VehicleDto vehicleDto = null;
        try{
            if(null!=id) {
                vehicle = vehicleRepository.getVehicleById(id);
            }if(null!=vehicle){
                vehicleDto = customObjectMapper.toVehicleDto(vehicle);
            }
                //null
        }catch(Exception e){
            logger.error("An error occurred while getting single vehicle data", e);

        }
        logger.info("Receiving vehicle if available");
        return vehicleDto;
    }

    @Override
    public List<VehicleDto> getAllVehicle() {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        try{
            List<Vehicle> vehicleList= vehicleRepository.findAll();
           if(CollectionUtils.isNotEmpty(vehicleList)){
                vehicleDtoList= customObjectMapper.toVehicleDtoList(vehicleList);
           }
        }catch (Exception e){
            logger.error("Error in getAllVehicle method",e);
            throw new ResourceAccessException("Cannot find record in database");
        }
        return  vehicleDtoList;
    }

    @Override
    public Boolean deleteVehicle(Long id) {//charge while deleting
        Vehicle vehicle = null;
        try{
            vehicle = vehicleRepository.getVehicleById(id);
            vehicleRepository.delete(vehicle);
            return true;
        }catch (Exception exception){
            logger.error("Error in deleteVehicle method", exception);
            throw new NoSuchElementException("Record does not exists in database");
        }
        //return false;
    }




}
