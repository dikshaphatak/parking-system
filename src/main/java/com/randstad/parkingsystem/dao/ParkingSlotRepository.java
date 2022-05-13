package com.randstad.parkingsystem.dao;

import com.randstad.parkingsystem.model.ParkingSlot;
import com.randstad.parkingsystem.model.dto.SlotDto;
import com.randstad.parkingsystem.model.enums.Availability;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    ParkingSlot findBySlotNumber(Integer slotNumber);

    ParkingSlot getParkingSlotById(Long id);

    ParkingSlot getParkingSlotByIdAndAvailability(Long slotId,Availability availability);

    @Query("select new com.randstad.parkingsystem.model.ParkingSlot(ps.slotNumber,ps.slotType, ps.availability) " +
            "from ParkingSlot ps where  ps.availability=:availability order by ps.slotNumber asc")//'UNOCCUPIED'
    List<ParkingSlot> getParkingSlotByAvailability(@Param("availability")Availability availability);


    List<ParkingSlot> getParkingSlotByAvailabilityOrderBySlotNumberAsc(Availability availability);
}
