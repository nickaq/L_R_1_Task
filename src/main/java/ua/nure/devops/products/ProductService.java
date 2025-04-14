package ua.nure.devops.products;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Long id, Product newProductData) {
        return repository.findById(id)
                .map(product -> {
                    product.setName(newProductData.getName());
                    product.setPrice(newProductData.getPrice());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    newProductData.setId(id);
                    return repository.save(newProductData);
                });
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}