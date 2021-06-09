package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.PocketServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.PocketRequest;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pocket")
public class PocketController {
    @Autowired
    private PocketServiceImpl pocketService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllPockets(){
        List<PocketResponse> listPocket = pocketService.getAllPocket();

        return new ResponseEntity<>(
                new ApiResponse("success get pocket data", listPocket),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getPocketsByID(@PathVariable String id){
        PocketResponse detailPocket = pocketService.getDetailPocketByID(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail pocket data", detailPocket),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createPocket(@RequestBody PocketRequest pocketRequest){
        PocketResponse createPocket = pocketService.createPocket(pocketRequest);

        return new ResponseEntity<>(
                new ApiResponse("success create pocket data", createPocket),
                HttpStatus.OK
        );
    }


}
