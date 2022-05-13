package com.randstad.parkingsystem.model;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "activeflag", columnDefinition = "tinyint(1) DEFAULT 1", insertable = false, nullable = false)//not null
    private Boolean activeFlag;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(value = GenerationTime.INSERT)
    @Column(name = "created", columnDefinition = "datetime default CURRENT_TIMESTAMP", insertable = false, nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "lastupdated", columnDefinition = "datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false,
            nullable = false)
    private Date lastUpdated;

    @Column(name = "version", columnDefinition = "int(10) DEFAULT 0", nullable = false)
    @Version
    private Integer version;

    public Date getCreated() {
        if(this.created==null){
            this.created = DateTime.now().toDate();
        }
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdated() {
        if(this.lastUpdated==null){
            this.lastUpdated = DateTime.now().toDate();
        }
        return this.lastUpdated;
    }
}
