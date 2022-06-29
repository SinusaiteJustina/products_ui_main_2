package lt.bit.products.ui.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ProductStoreClient {

    @Value("${endpoint.productstore.products_count}")
    private String productsCountUrl;

    @Value("${endpoint.productstore.products}")
    private String productsUrl;



    private final RestTemplate restTemplate;

    public ProductStoreClient( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public long getProductsCount() {
        return restTemplate.exchange(productsCountUrl, HttpMethod.GET, null, Long.class).getBody();
    }

    public List<ProductStoreResponse> getProducts() {
        ResponseEntity<List<ProductStoreResponse>> response = restTemplate.exchange(productsUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductStoreResponse>>(){});
        return response.getBody();
    }
}
