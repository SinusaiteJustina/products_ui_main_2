package lt.bit.products.ui.configuration;

import lt.bit.products.ui.service.SupplierService;
import lt.bit.products.ui.service.SupplierServiceH2;
import lt.bit.products.ui.service.SupplierServiceMock;
import lt.bit.products.ui.service.domain.ProductRepository;
import lt.bit.products.ui.service.domain.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
class GlobalConfiguration implements WebMvcConfigurer {

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Primary
  @Bean("supplierServiceH2")
  SupplierService supplierServiceH2(SupplierRepository repository, ModelMapper mapper, ProductRepository productRepository) {
    return new SupplierServiceH2(repository, mapper, productRepository);
  }

  @Bean("supplierServiceMock")
  SupplierService supplierServiceMock() {
    return new SupplierServiceMock();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
