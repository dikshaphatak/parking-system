package com.randstad.parkingsystem.model.dto;

import com.randstad.parkingsystem.model.enums.Availability;
import com.randstad.parkingsystem.model.enums.SlotType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SlotDto {
    private Long id;

    @NotNull(message = "Cannot be null")
    private Integer slotNumber;

    @NotEmpty
    private String slotType;
    private String availability;
}
