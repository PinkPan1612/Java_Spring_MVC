package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration // Đánh dấu lớp này là một lớp cấu hình Spring
@EnableWebMvc // Kích hoạt cấu hình Spring MVC mặc định
public class WebMvcConfig implements WebMvcConfigurer { // Triển khai giao diện WebMvcConfigurer để tùy chỉnh cấu hình
                                                        // Spring MVC

    @Bean // Đánh dấu phương thức này để Spring quản lý và tạo bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver(); // Tạo một
                                                                                      // InternalResourceViewResolver
        bean.setViewClass(JstlView.class); // Sử dụng JSTL để render các view
        bean.setPrefix("/WEB-INF/view/"); // Đặt tiền tố cho đường dẫn tới các file view
        bean.setSuffix(".jsp"); // Đặt hậu tố cho các file view
        return bean; // Trả về bean đã cấu hình
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) { // Ghi đè phương thức để tùy chỉnh cấu hình view
                                                                        // resolver
        // ...existing code...
    }
}