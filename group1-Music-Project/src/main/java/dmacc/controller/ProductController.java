package dmacc.controller;

import dmacc.beans.Product;
import dmacc.repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepo;
    
    @GetMapping("/Success")
    public String Success() {
        return "redirect:/";
    }

    @GetMapping("/AddProduct") //Currently used to test adding products
    public String AddProduct(Model model) {
        Product p = new Product();
        model.addAttribute("NewProduct", p);
        return "AddProduct";
    }

    @PostMapping("/AddProduct")
    public String AddProduct(@ModelAttribute Product p, Model model) {
        productRepo.save(p);
        return "redirect:/Success";
    }

    @GetMapping("/ViewAllProducts")
    public String ViewAllProducts(Model model) {
        if(productRepo.findAll().isEmpty()) {
            return "home";
        }

        model.addAttribute("products", productRepo.findAll());

        return "AllProducts";
    }
    
    @GetMapping("/Search")
    public List<Product> search(String keyword) {
        if (keyword != null) {
            return productRepo.findByKeyword(keyword);
        }
        return productRepo.findAll();
    }
    
    @PostMapping("/Search")
    public String viewProducts(Model model, @Param("keyword") String keyword) {
        List<Product> listProducts = productRepo.findByKeyword(keyword);
        model.addAttribute("products", listProducts);
        model.addAttribute("keyword", keyword);
         
        return "AllProducts";
    }
}
