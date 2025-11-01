package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.Product;
import edu.dosw.petcare.PetCare360.model.entities.ProductCategory;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
            product.setActive(true);
        }
        products.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public List<Product> findByCategory(ProductCategory category) {
        return products.values().stream()
                .filter(p -> p.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Product> findByActiveTrue() {
        return products.values().stream()
                .filter(Product::getActive)
                .collect(Collectors.toList());
    }

    public boolean existsById(Long id) {
        return products.containsKey(id);
    }

    public void deleteById(Long id) {
        products.remove(id);
    }
}