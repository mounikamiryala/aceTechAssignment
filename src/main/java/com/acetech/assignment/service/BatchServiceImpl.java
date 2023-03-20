package com.acetech.assignment.service;

import com.acetech.assignment.pojos.Batch;
import com.acetech.assignment.pojos.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private NotificationService notificationService;

    public Map<Batch, String> invalidBatchesReason = new HashMap<>();

    @Override
    public ResponseEntity<?> createBatch(List<Batch> batches) {

        Map<Boolean, List<Batch>> partitionedBatches = batches.stream()
                .collect(Collectors.partitioningBy(this::isValidBatch));

        List<Batch> validBatches = partitionedBatches.get(true);
        List<Batch> invalidBatches = partitionedBatches.get(false);

        List<BatchStatus> batchStatusList = new ArrayList<>();

        invalidBatches.forEach(batch -> {
            String invalidBatchReason = invalidBatchesReason.get(batch);
            BatchStatus status = new BatchStatus(batch, "Batch is Not Created due to validation errors.", invalidBatchReason);
            notificationService.notifyBatchCreated(status);
            batchStatusList.add(status);
        });

        validBatches.forEach(batch -> {
            // save to db
            //if saved successfully batch is created
            //else not created, send reason
            BatchStatus status = new BatchStatus(batch, "Batch is Created successfully!", "");
            notificationService.notifyBatchCreated(status);
            batchStatusList.add(status);
        });

        if (validBatches.isEmpty()) {
            return ResponseEntity.badRequest().body(batchStatusList);
        }

        return ResponseEntity.ok(batchStatusList);
    }

    private boolean isValidBatch(Batch batch) {
        String errorMsg = null;

        if (batch.getBatchId() == null || batch.getBatchId().isEmpty()) {
            errorMsg = "BatchId cannot be null or empty.";
        }
        if (batch.getBatchTypeId() == null || batch.getBatchTypeId().isEmpty()) {
            errorMsg = (errorMsg == null ? "" : errorMsg + " ") + "BatchTypeId cannot be null or empty.";
        }
        if (batch.getBatchTypeDescription() == null || batch.getBatchTypeDescription().isEmpty()) {
            errorMsg = (errorMsg == null ? "" : errorMsg + " ") + "BatchTypeDescription cannot be null or empty.";
        }
        if (batch.getBatchExpirationDate() == null || batch.getBatchExpirationDate().isBefore(LocalDate.now())) {
            errorMsg = (errorMsg == null ? "" : errorMsg + " ") + "BatchExpirationDate cannot be null or pastValue.";
        }
        if (batch.getBatchCount() <= 0) {
            errorMsg = (errorMsg == null ? "" : errorMsg + " ") + "BatchCount must be greater than zero.";
        }
        if (errorMsg != null) {
            invalidBatchesReason.put(batch, errorMsg);
            return false;
        }
        return true;
    }

}