package com.acetech.assignment.service;

import com.acetech.assignment.pojos.Batch;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BatchService {

    public ResponseEntity<?> createBatch(List<Batch> batches);
}