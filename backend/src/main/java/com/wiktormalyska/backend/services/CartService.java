package com.wiktormalyska.backend.services;

import com.wiktormalyska.backend.dao.ICartRepository;
import com.wiktormalyska.backend.dao.IItemRepository;
import com.wiktormalyska.backend.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private IItemRepository itemRepository;
    @Autowired
    private UserService userService;

    public void addToCart(int itemID, int quantity) {
        cartRepository.addToCart(userService.getCurrentUser().getCart(), itemID, quantity);
    }

    public Cart getCart() {
        return userService.getCurrentUser().getCart();
    }

    public void removeFromCart(int itemId) {
        cartRepository.removeFromCart(itemId);
    }

}
