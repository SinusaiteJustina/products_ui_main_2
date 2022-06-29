package lt.bit.products.ui.integration;

import java.time.LocalDate;

public class ProductStoreResponse {

        private Integer id;
        private String name;
        private String description;
        private LocalDate created;


        public Integer getId() {
            return id;
        }

        public ProductStoreResponse setId(Integer id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public ProductStoreResponse setName(String name) {
            this.name = name;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public ProductStoreResponse setDescription(String description) {
            this.description = description;
            return this;
        }

        public LocalDate getCreated() {
            return created;
        }

        public ProductStoreResponse setCreated(LocalDate created) {
            this.created = created;
            return this;
        }
    }

