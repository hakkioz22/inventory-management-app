package com.tpe.controller;

import com.tpe.domain.Product;
import com.tpe.exception.ResourceNotAvailable;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findExistingProduct(@PathVariable Long id){
        Product product = productService.findProduct(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(id, updatedProduct);

        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Taking products from inventory
    @PutMapping("/{id}/remove")
    public ResponseEntity<Product> removeProductFromInventory(@PathVariable Long id, @RequestParam int quantity) {
        try {
            Product updatedProduct = productService.removeProductFromInventory(id, quantity);
            return ResponseEntity.ok(updatedProduct);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ResourceNotAvailable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //Update specific product's quantity
    @PutMapping("/{id}/add")
    public ResponseEntity<Product> addNewQuantitySpecificProduct(@PathVariable Long id,@RequestParam int newQuantity){
        Product updatedProduct = productService.updateProductQuantity(id, newQuantity);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }
}
