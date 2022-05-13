package com.randstad.parkingsystem.service.impl;

import com.randstad.parkingsystem.dao.ParkingSlotRepository;
import com.randstad.parkingsystem.exception.ServiceException;
import com.randstad.parkingsystem.mapper.CustomObjectMapper;
import com.randstad.parkingsystem.model.ParkingSlot;
import com.randstad.parkingsystem.model.dto.SlotDto;
import com.randstad.parkingsystem.model.enums.Availability;
import com.randstad.parkingsystem.model.enums.SlotType;
import com.randstad.parkingsystem.service.IParkingSlotService;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ParkingSlotServiceImpl implements IParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    CustomObjectMapper customObjectMapper;

    @Value("${vehicle.type.two_wheeler}")
    String vehicleType;

    Logger logger = LoggerFactory.getLogger(ParkingSlotServiceImpl.class);

    public ParkingSlotServiceImpl(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @Override
    public List<SlotDto> getAllSlots() {
        logger.info("Getting list of all slots");
        //null
        List<SlotDto> slotDtoList = new ArrayList<>();
        try {
            List<ParkingSlot> slotList = parkingSlotRepository.findAll(Sort.by(Sort.Order.asc("slotNumber")));//null
            if (CollectionUtils.isNotEmpty(slotList)) {
                slotDtoList = customObjectMapper.toSlotDtoList(slotList);
                for (SlotDto slotDto:slotDtoList) {
                    if(StringUtils.equals(slotDto.getSlotType(),vehicleType)){
                        slotDto.setSlotType("Two-Wheeler");
                    }else {
                        slotDto.setSlotType("Four-Wheeler");
                    }
                }
            }
        } catch (Exception exception) {
            logger.error("Error inside getSlots method of ParkingSlotService");
        }
        return slotDtoList;
    }


    @Override
    public SlotDto saveSlot(SlotDto slotDto){
        logger.info("Inside saveSlot method of ParkingSlotService");
        ParkingSlot slot= null;
        SlotDto newSlotDto = null;
        try{
            if(null!=slotDto && null!=slotDto.getSlotNumber()){
                logger.info("slotDto is not Null");
                slot = parkingSlotRepository.findBySlotNumber(slotDto.getSlotNumber());
                if(null==slot) {
                    slot = new ParkingSlot();
                    logger.info("Adding new Slot");
                    slot = customObjectMapper.toParkingSlot(slotDto, slot);
                    logger.info("Saving slot");
                    slot = parkingSlotRepository.save(slot);
                    newSlotDto = customObjectMapper.toSlotDto(slot);
                    if(StringUtils.equals(slotDto.getSlotType(),vehicleType)){
                        newSlotDto.setSlotType("Two-Wheeler");
                    }else {
                        newSlotDto.setSlotType("Four-Wheeler");
                    }
                }
            }
        }catch(Exception e){
            logger.error("An exception occurred in saveSlot method of ParkingSlotService", e);
            throw new ServiceException("Encountered null values");
        }
        return newSlotDto;
    }

    @Override
    public SlotDto getSingleSlot(Integer slotNumber) {
        ParkingSlot existingSlot= new ParkingSlot();
        SlotDto slotDto = null;
        try{
           if(null!=slotNumber){
                existingSlot = parkingSlotRepository.findBySlotNumber(slotNumber);//null
                slotDto = customObjectMapper.toSlotDto(existingSlot);//null
           }
        }catch(Exception e){
            logger.error("An error occurred while getting single slot", e);

        }
        logger.info("Receiving Slot if available");
        return slotDto;
    }

    @Override
    public Boolean deleteSlot(Integer slotNumber) {//4
        logger.info("Deleting Slot from parking system");
        ParkingSlot parkingSlot = new ParkingSlot();
        try{
            if(null!=slotNumber ){
                parkingSlot = parkingSlotRepository.findBySlotNumber(slotNumber);
                if (null!=parkingSlot){
                    parkingSlotRepository.delete(parkingSlot);

                    logger.info("Slot deleted");
                    return true;
                }
            }
        }catch (Exception e){
            logger.error("Error while deleting parking slot", e);
            throw new NoSuchElementException("Does not exists in database");
        }
        return false;
    }

    @Override
    public List<SlotDto> getUnoccupiedSlots() {
        logger.info("Getting list of unoccupied slots");
        List<SlotDto> unoccupiedSlotDtoList = new ArrayList<>();
        try {
            List<ParkingSlot> parkingSlotList = parkingSlotRepository.getParkingSlotByAvailabilityOrderBySlotNumberAsc(Availability.UNOCCUPIED);
            unoccupiedSlotDtoList = customObjectMapper.toSlotDtoList(parkingSlotList);
//            if (CollectionUtils.isNotEmpty(slotList)) {

        } catch (Exception exception) {
            logger.error("Error inside getSlots method of ParkingSlotService");
        }
        return unoccupiedSlotDtoList;
    }

    @Override
    public Boolean checkSlot(Integer slotNumber) {
        boolean flag=false;
        ParkingSlot existingSlot= new ParkingSlot();
        try{
            if(null!=slotNumber) {
                existingSlot = parkingSlotRepository.findBySlotNumber(slotNumber);
            }
            if(null!=existingSlot){
                flag=true;
            }
        }catch(Exception e){
            logger.error("Data does not exixts in database", e);
        }
        logger.info("Receiving Slot if available");
        return flag;
    }
}
