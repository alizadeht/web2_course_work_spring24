package com.example.shoppingapp.controller;

import com.example.shoppingapp.model.Product;
import com.example.shoppingapp.service.ProductService;
import com.example.shoppingapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"userName", "cartProducts"})
public class AppController {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("userName", "Guest"); // Default user name
        return "index";
    }

    @PostMapping("/greet")
    public String greetUser(@RequestParam String userName, Model model) {
        model.addAttribute("userName", userName);
        return "redirect:/";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.listAllProducts());
        return "products";
    }

    // This is a simplistic approach for cart management. You might need a more sophisticated cart management in a real application.
    @GetMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, Model model) {
        List<Product> cartProducts = (List<Product>) model.getAttribute("cartProducts");
        if (cartProducts == null) {
            cartProducts = new ArrayList<>();
        }
        productService.findProductById(productId).ifPresent(cartProducts::add);
        model.addAttribute("cartProducts", cartProducts);
        return "redirect:/products";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<Product> cartProducts = (List<Product>) model.getAttribute("cartProducts");
        if (cartProducts == null) {
            cartProducts = new ArrayList<>();
        }
        model.addAttribute("cartProducts", cartProducts);
        // Calculate total weight and price
        double totalWeight = cartProducts.stream().mapToDouble(Product::getWeight).sum();
        double totalPrice = cartProducts.stream().mapToDouble(Product::getPrice).sum();
        model.addAttribute("totalWeight", totalWeight);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.listAllOrders());
        return "orders";
    }

    @GetMapping("/checkout")
    public String checkout(SessionStatus status) {
        status.setComplete(); // Clear session attributes after checkout
        return "redirect:/";
    }
}
