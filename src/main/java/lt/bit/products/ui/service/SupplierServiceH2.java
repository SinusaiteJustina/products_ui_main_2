package lt.bit.products.ui.service;

import java.util.List;
import java.util.UUID;
import lt.bit.products.ui.model.Product;
import lt.bit.products.ui.model.Supplier;
import lt.bit.products.ui.service.domain.ProductEntity;
import lt.bit.products.ui.service.domain.SupplierEntity;
import lt.bit.products.ui.service.domain.SupplierRepository;
import lt.bit.products.ui.service.error.ErrorCode;
import lt.bit.products.ui.service.error.ValidationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SupplierServiceH2 implements SupplierService {

  private final SupplierRepository repository;
  private final ModelMapper mapper;

  public SupplierServiceH2(SupplierRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<Supplier> getSuppliers() {
    List<SupplierEntity> suppliers = repository.findAll();
    // @formatter:off
    return mapper.map(suppliers, new TypeToken<List<Supplier>>() {}.getType());
    // @formatter:on
  }

  @Override
  public Supplier getSupplier(UUID id) {
    return repository.findById(id).map(s -> mapper.map(s, Supplier.class)).orElseThrow();
  }

  @Override
  public void saveSupplier(Supplier supplier) /*throws ValidationException*/ {
    UUID id = supplier.getId();
//    if (id != null && !repository.existsById(id)) {
//      throw new ValidationException(ErrorCode.PRODUCT_NOT_FOUND, id);
//    }
    repository.save(mapper.map(supplier, SupplierEntity.class));
  }
}
