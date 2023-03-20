package com.acetech.assignment.pojos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BatchStatus implements Serializable {
    private Batch batch;
    private String batchCreationStatus;
    private String batchValidationErrors;

}

