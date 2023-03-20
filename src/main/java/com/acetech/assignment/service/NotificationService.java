package com.acetech.assignment.service;

import com.acetech.assignment.pojos.BatchStatus;


public interface NotificationService {


    void notifyBatchCreated(BatchStatus batchStatus);
}