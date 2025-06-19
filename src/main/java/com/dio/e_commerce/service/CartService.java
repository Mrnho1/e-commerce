package com.dio.e_commerce.service;

import com.dio.e_commerce.model.Cart;
import com.dio.e_commerce.model.Product;
import com.dio.e_commerce.model.User;
import com.dio.e_commerce.repository.CartRepository;
import com.dio.e_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

    public Cart addProductToCart(Long productId, Authentication auth) {
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Product product = productService.findById(productId);

        Cart cart = user.getCart();
        cart.getProdutos().add(product);

        return cartRepository.save(cart);
    }
}
