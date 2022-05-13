package com.randstad.parkingsystem.model;

import com.randstad.parkingsystem.model.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle")
@Data
@SQLDelete(sql = "UPDATE vehicle SET activeflag=0 WHERE id=? and version=?")
@Where(clause = "1=activeflag")
public class Vehicle extends BaseEntity implements Serializable {

    @Column(name="vehicle_number", length = 30, nullable = false)
    private String vehicleNumber;

    @Column(name="owner_name", length = 30)
    private String ownerName;

    @Column(name="contact_number", length = 15)
    private String contactNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="slot_id", referencedColumnName = "id",nullable = false )
    private ParkingSlot slot;//slot

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="park_in_time", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP",insertable = false)
    private Date parkInTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="park_out_time", columnDefinition = "datetime")
    private Date parkOutTime ;

    @Column(name="parking_charge")
    private Double parkingCharge;

    @Column(name="payment_mode", length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @Column(name = "park_in_time")
    @Generated(GenerationTime.INSERT)
    public Date getParkInTime() {
        if(null==this.parkInTime){
            this.parkInTime = DateTime.now().toDate();
        }
        return this.parkInTime;
    }
}
