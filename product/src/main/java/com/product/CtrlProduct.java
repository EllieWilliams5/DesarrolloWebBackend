package com.product;

// import com.product.Category;
// import com.product.CategoryManager;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CtrlProduct {

	private CategoryManager catMan;
	
	public CtrlProduct() {	
		catMan = new CategoryManager();
		catMan.createCategory(new Category("Lentes","Lts"));
		catMan.createCategory(new Category("Relojes","Rljs"));
		catMan.createCategory(new Category("Relojes","R"));
	}
	
	
    @GetMapping
    public List<Category> darCategorias() {
        return catMan.getCategories();
    }
}
