package com.randstad.parkingsystem.dao;

import com.randstad.parkingsystem.model.Vehicle;
import com.randstad.parkingsystem.model.dto.Parking;
import com.randstad.parkingsystem.model.dto.ParkingHistoryDto;
import com.randstad.parkingsystem.model.enums.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByVehicleNumber(String vehicleNumber);
    Vehicle getVehicleById(Long id);

    @Query("select new com.randstad.parkingsystem.model.dto.Parking(v.id, v.slot.slotNumber, v.slot.slotType, v.ownerName, v.contactNumber, v.vehicleNumber, v.parkInTime, v.parkOutTime, v.slot.availability ) " +
            "from Vehicle v where v.slot.availability=:availability and v.parkOutTime is null order by v.slot.slotNumber")//v.slot.availability 'OCCUPIED'
    List<Parking> getParkingDtos(@Param("availability") Availability availability);

    @Query("select new com.randstad.parkingsystem.model.dto.ParkingHistoryDto( v.slot.slotNumber,  v.vehicleNumber, v.ownerName, v.contactNumber, v.parkInTime, v.parkOutTime, v.parkingCharge ) " +
            "from Vehicle v where v.parkOutTime is not null order by v.parkOutTime desc")
    List<ParkingHistoryDto> getParkingHistoryDtos();


}
