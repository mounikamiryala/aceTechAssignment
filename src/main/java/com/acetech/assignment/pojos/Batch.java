package com.acetech.assignment.pojos;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Batch implements Serializable {
    private String batchId;
    private String batchTypeId;
    private String batchTypeDescription;
    private LocalDate batchExpirationDate;
    private int batchCount;

}

