package lt.bit.products.ui.jobs;

import lt.bit.products.ui.integration.ProductStoreClient;
import lt.bit.products.ui.integration.ProductStoreResponse;
import lt.bit.products.ui.service.ProductService;
import lt.bit.products.ui.service.domain.ProductEntity;
import lt.bit.products.ui.service.domain.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class ProductsImporter {
    private final ProductStoreClient productStoreClient;

    private final ProductService productService;

    private final ProductRepository productRepository;

    private final static Logger LOG = LoggerFactory.getLogger(ProductsImporter.class);

    ProductsImporter(ProductStoreClient productStoreClient, ProductService productService,
                     ProductRepository productRepository) {
        this.productStoreClient = productStoreClient;
        this.productService = productService;
        this.productRepository = productRepository;
    }


    boolean noNewProducts() {
        Long countStoreProducts = productStoreClient.getProductsCount();
        Long countUiProducts = productService.countProducts();

        return countStoreProducts <= countUiProducts;

//
//        LOG.info("Products Importer job started");
//        LOG.info("Number of products: {}", countStoreProducts);
//        LOG.info("Products importer completed ");
    }

//    @Scheduled(fixedRateString = "60000")
    void importProducts() {
        if (noNewProducts()) {
        return;
    }
        LOG.info("Importing products...");

        List<ProductStoreResponse> newProducts = productStoreClient.getProducts();
        List<ProductEntity> existingProducts = productRepository.findAll();
        Set<Integer> collectedExternalId = existingProducts.stream()
                .map(ProductEntity::getExternalId)
                .filter(Objects::nonNull)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
        List<ProductEntity> productToSave = newProducts.stream()
                    .filter(pr -> !collectedExternalId.contains(pr.getId()))
                    .map(storeProduct -> {
                    ProductEntity entity = new ProductEntity();
                    entity.setName(storeProduct.getName());
                    entity.setDescription(storeProduct.getDescription());
                    entity.setExternalId(String.valueOf(storeProduct.getId()));
                    return entity;
                })
                .collect(Collectors.toList());
        productRepository.saveAll(productToSave);
        LOG.info("Imported {} products.", productToSave.size());
    }

}
