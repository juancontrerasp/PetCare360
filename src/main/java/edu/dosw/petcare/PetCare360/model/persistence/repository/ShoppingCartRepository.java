package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.ShoppingCart;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ShoppingCartRepository {
    private final Map<Long, ShoppingCart> carts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ShoppingCart save(ShoppingCart cart) {
        if (cart.getId() == null) {
            cart.setId(idGenerator.getAndIncrement());
        }
        carts.put(cart.getId(), cart);
        return cart;
    }

    public Optional<ShoppingCart> findById(Long id) {
        return Optional.ofNullable(carts.get(id));
    }

    public Optional<ShoppingCart> findByUserId(Long userId) {
        return carts.values().stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst();
    }

    public void deleteById(Long id) {
        carts.remove(id);
    }

    public void deleteByUserId(Long userId) {
        carts.values().removeIf(cart -> cart.getUserId().equals(userId));
    }
}