package com.project4.helper;

import com.google.common.hash.Hashing;
import com.project4.entity.CartEntity;
import com.project4.entity.CategoryEntity;
import com.project4.entity.ProductEntity;
import com.project4.entity.UserEntity;
import com.project4.repository.CategoryRepository;
import com.project4.repository.ProductRepository;
import com.project4.repository.RoleRepository;
import com.project4.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class CafeMapDataJSON {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public CafeMapDataJSON(){}

    public UserEntity getUserFromMap(Map<String, String> requestMap){
        UserEntity user = new UserEntity();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setPhone(requestMap.get("phone"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("true");
        user.setRole(roleRepository.findByName("user"));
        return user;
    }

    public UserEntity getProfileFromMap(Map<String, String> requestMap){
        UserEntity user = new UserEntity();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setAddress(requestMap.get("address"));
        user.setBirthday(LocalDate.parse(requestMap.get("birthday")));
        return user;
    }

    public CategoryEntity getCategoryFromMap(Map<String, String> requestMap){
        CategoryEntity category = new CategoryEntity();
        category.setName(requestMap.get("name"));
        return category;
    }

    public ProductEntity getProductEntityFromMap(Map<String, String> requestMap){
        ProductEntity product = new ProductEntity();

        DateTimeFormatter typeDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(LocalDateTime.now().format(typeDate), typeDate);

        product.setImage(requestMap.get("image"));
        product.setName(requestMap.get("name"));
        product.setQuantity(Integer.parseInt(requestMap.get("quantity")));
        product.setPrice(Double.parseDouble(requestMap.get("price")));
        product.setDescription(requestMap.get("description"));
        product.setStatus("true");
        product.setCreateBy(jwtFilter.getCurrentUser());
        product.setCreateAt(date);
        product.setCategory(categoryRepository.findById(Long.parseLong(requestMap.get("category_id"))).get());

        return product;
    }

    public ProductEntity getProductEntityUpdateFromMap(Map<String, String> requestMap){
        DateTimeFormatter typeDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(LocalDateTime.now().format(typeDate), typeDate);

        ProductEntity product = productRepository.findById(Long.parseLong(requestMap.get("id"))).get();
        if(requestMap.get("image") != null){
            product.setImage(requestMap.get("image"));
        }
        product.setName(requestMap.get("name"));
        product.setQuantity(Integer.parseInt(requestMap.get("quantity")));
        product.setPrice(Double.parseDouble(requestMap.get("price")));
        product.setDescription(requestMap.get("description"));
        product.setModifyBy(jwtFilter.getCurrentUser());
        product.setModifyAt(date);
        product.setCategory(categoryRepository.findById(Long.parseLong(requestMap.get("category_id"))).get());

        return product;
    }

    public String uploadAndGetImageName(MultipartFile fileImage){
        DateTimeFormatter typeTimeImage = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timeImage = LocalDateTime.now().format(typeTimeImage);

        String nameImage = Hashing.sha256().hashString(timeImage+fileImage.getOriginalFilename(), StandardCharsets.UTF_8).toString()+"."+getAndCheckExtensionFile(fileImage.getOriginalFilename());
        try {
            Path path = Paths.get(CafeConstant.URL_PATH_IMAGE + nameImage);
            Files.copy(fileImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
        }
        return nameImage;
    }

    public String getAndCheckExtensionFile(String fileName){
        int lastDot = fileName.lastIndexOf(".");
        String extension = "";
        if(lastDot > 0){
            extension = fileName.substring(lastDot+1);
            List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png");
            for(String e : validExtensions){
                if(extension.equalsIgnoreCase(e)){
                    return extension;
                }
            }
        }
        return null;
    }

    public boolean isValidFileSize(long size){
        long validSize = 2 * 1024 * 1024;
        if(size <= validSize){
            return true;
        }
        return false;
    }

    public CartEntity getCartFromMap(UserEntity user, Map<String, String> requestMap){
        CartEntity cart = new CartEntity();
        ProductEntity product = productRepository.findById(Long.parseLong(requestMap.get("product_id"))).get();
        int quantity = Integer.parseInt(requestMap.get("quantity"));
        cart.setUserCart(user);
        cart.setProductCart(product);
        cart.setQuantity(quantity);
        cart.setTotal((double) (quantity*product.getPrice()));
        return cart;
    }

    public CartEntity getCartUpdateFromMap(Map<String, String> requestMap, CartEntity cart){
        int quantity = Integer.parseInt(requestMap.get("quantity"));
        cart.setQuantity(quantity);
        cart.setTotal((double) (quantity*cart.getProductCart().getPrice()));
        return cart;
    }
}
