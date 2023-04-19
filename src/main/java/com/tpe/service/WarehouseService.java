package com.tpe.service;

import com.tpe.domain.Warehouse;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    //Get all warehouses
    public List<Warehouse> getAllWarehouse(){
        return warehouseRepository.findAll();
    }

    //Find an existing warehouse by id
    public Warehouse findWarehouseById(Long id){
        return warehouseRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Warehouse not found with id: " + id));
    }

    //Create a new Warehouse
    public Warehouse addWarehouse(Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

    //Update an existing warehouse
    public Warehouse updateWarehouse(Long id,Warehouse updatedWarehouse){
       return warehouseRepository.findById(id).
                map(warehouse -> {warehouse.setName(updatedWarehouse.getName());
                warehouse.setCity(updatedWarehouse.getCity());
                warehouse.setAddress(updatedWarehouse.getAddress());
                warehouse.setRegion(updatedWarehouse.getRegion());
                return warehouseRepository.save(warehouse);
                }).orElseThrow(()->new ResourceNotFoundException("Warehouse doesnt exist with id: " + id));
    }

    //Delete a warehouse
    public void deleteWarehouse(Long id){
        warehouseRepository.deleteById(id);
    }
}
