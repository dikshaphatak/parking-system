package com.randstad.parkingsystem.model;

import com.randstad.parkingsystem.model.enums.Availability;
import com.randstad.parkingsystem.model.enums.SlotType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@SQLDelete(sql = "UPDATE parking_slot SET activeflag= 0 WHERE id=? and version=?")
@Where(clause = "1=activeflag")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="parking_slot")
public class ParkingSlot extends BaseEntity implements Serializable {

    @Column(name = "slot_number", length = 3, nullable = false)
    private Integer slotNumber;

    @Column(name = "slot_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private SlotType slotType;

    @Column(name = "availability", columnDefinition = "varchar(15) default 'UNOCCUPIED'", insertable = false)
    @Enumerated(EnumType.STRING)
    private Availability availability;

    public Availability getAvailability() {
        if (null == this.availability) {
            this.availability = Availability.UNOCCUPIED;
        }
        return this.availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

}
