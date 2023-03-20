package com.acetech.assignment.controller;
import java.util.List;


import com.acetech.assignment.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.acetech.assignment.pojos.Batch;


@RestController
@RequestMapping("/acetech/batches")
public class BatchProcessingController {
	
	@Autowired
    private BatchService batchService;

    @PostMapping("/create")
    public ResponseEntity<?> createBatch(@RequestBody List<Batch> batches) {
        return batchService.createBatch(batches);
    }

}