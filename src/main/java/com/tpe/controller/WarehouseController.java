package com.tpe.controller;

import com.tpe.domain.Category;
import com.tpe.domain.Warehouse;
import com.tpe.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;


    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses(){
        List<Warehouse> warehouseList = warehouseService.getAllWarehouse();
        return new ResponseEntity<>(warehouseList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> findWarehouse(@PathVariable Long id){
        Warehouse warehouse = warehouseService.findWarehouseById(id);
        return new ResponseEntity<>(warehouse,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse savedWarehouse = warehouseService.addWarehouse(warehouse);
        return new ResponseEntity<>(savedWarehouse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id,@RequestBody Warehouse updatedWarehouse){
        Warehouse warehouse = warehouseService.updateWarehouse(id, updatedWarehouse);
        return new ResponseEntity<>(warehouse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable Long id){
        warehouseService.deleteWarehouse(id);
        return new ResponseEntity<>("Deleted successfully!",HttpStatus.OK);
    }
}
