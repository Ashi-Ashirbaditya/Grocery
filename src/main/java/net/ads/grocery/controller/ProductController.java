package net.ads.grocery.controller;

import net.ads.grocery.model.Category;
import net.ads.grocery.model.Products;
import net.ads.grocery.service.ProductService;
import net.ads.grocery.service.CategoryService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    private Category CategoryService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/product")
    public String viewHomePage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("products", new Products());
        return findPaginated(1, "id", "asc", model);
    }

    //show Category
    @GetMapping("/category")
    public String viewCategoryPage(Model model) {
        model.addAttribute("category", categoryService.getAllCategory());
        model.addAttribute("category", new Category());
        return findPaginated2(1, "id", "asc", model);
    }

    // Show form to add a new Product
    @GetMapping("/showNewProductsForm")
    public String showNewProductForm(Model model) {
        Products product = new Products();
        model.addAttribute("products", product);
        model.addAttribute("category", categoryService.getAllCategory());
        return "new-product";
    }


    // Show form to add a new Category
    @GetMapping("/showNewCategoryForm")
    public String showNewCategoryForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "new-category";
    }

    // Save or update products
    @PostMapping("/saveProducts")
    public String saveProducts(@ModelAttribute("products") Products products) {
        productService.saveProducts(products);
        return "redirect:/product";
    }

    // Save or update category
    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/category";
    }

    // Show form to update a product
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable("id") Long id, Model model) {
        Products products = productService.getProductsById(id);
        model.addAttribute("products", products);
        model.addAttribute("category", categoryService.getAllCategory());
        return "update-product";
    }

    // Delete an product
    @GetMapping("/deleteProducts/{id}")
    public String deleteProducts(@PathVariable("id") long id) {
        this.productService.deleteProductsById(id);
        return "redirect:/product";
    }

    // Delete Category
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") long id) {
        this.categoryService.deleteCategoryById(id);
        return "redirect:/category";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 4;

        Page< Products > page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List< Products > listProducts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listProducts", listProducts);
        return "product";
    }

    @GetMapping("/page2/{pageNo2}")
    public String findPaginated2(@PathVariable(value = "pageNo2") int pageNo2,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 4;

        Page< Category > page = categoryService.findPaginated2(pageNo2, pageSize, sortField, sortDir);
        List< Category > listCategory = page.getContent();

        model.addAttribute("currentPage", pageNo2);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCategory", listCategory);
        return "category";
    }
}
