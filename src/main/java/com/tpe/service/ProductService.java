package com.tpe.service;

import com.tpe.domain.Action;
import com.tpe.domain.Product;
import com.tpe.domain.ProductHistory;
import com.tpe.exception.ResourceNotAvailable;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.ProductHistoryRepository;
import com.tpe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductHistoryRepository historyRepository;

    @Autowired
    private EmailService emailService;

    //Save every product's interaction
    private void saveProductHistory(Product product, String action) {
        ProductHistory history = new ProductHistory();
        history.setProduct(product);
        history.setAction(Action.valueOf(action));
        history.setQuantity(product.getQuantity());
        history.setTimestamp(LocalDateTime.now());

        historyRepository.save(history);
    }
    //Get all Products
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //Find an existing product by Id
    public Product findProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product does not exist with id: " + id));
    }

    //Create a new product
    public Product addProduct(Product product){
        Product savedProduct = productRepository.save(product);
        saveProductHistory(savedProduct,"ADD");
        return savedProduct;
    }

    //Update an existing product
    public Product updateProduct(Long id,Product updatedProduct){
        return productRepository.findById(id).
                map(product->{product.setName(updatedProduct.getName());
                product.setCategory(updatedProduct.getCategory());
                product.setQuantity(updatedProduct.getQuantity());
                product.setCriticalThreshold(updatedProduct.getCriticalThreshold());
                saveProductHistory(product,"UPDATE");
                return productRepository.save(product);
                }).orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + id));
    }

    //Delete an existing product
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        productRepository.deleteById(id);
        saveProductHistory(product,"DELETE");
    }

    //Removing a certain number of items from the inventory
    public Product removeProductFromInventory(Long productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            int currentQuantity = product.getQuantity();

            if (currentQuantity < quantity) {
                throw new ResourceNotAvailable("Requested quantity exceeds available inventory.");
            }

            int newQuantity = currentQuantity - quantity;
            product.setQuantity(newQuantity);
            Product updatedProduct = productRepository.save(product);

            if (newQuantity < product.getCriticalThreshold()) {
                emailService.sendMail(
                        "hakkioz@outlook.com",
                        "Kritik ürün seviyesi uyarısı",
                        product.getName() + " kritik seviyenin altına düştü. Su anki adet: " + product.getQuantity()
                );
            }
            saveProductHistory(updatedProduct,"UPDATE");
            return updatedProduct;
        } else {
            throw new ResourceNotFoundException("Product not found.");
        }
    }

    //Update specific product's quantitiy
    public Product updateProductQuantity(Long id,int newQuantity){
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()){
            Product product1 = product.get();
            product1.setQuantity(newQuantity);
            Product updatedProduct = productRepository.save(product1);
            saveProductHistory(updatedProduct,"UPDATE");
            return updatedProduct;
        }else {
            throw new ResourceNotFoundException("Product not found!");
        }
    }
}
