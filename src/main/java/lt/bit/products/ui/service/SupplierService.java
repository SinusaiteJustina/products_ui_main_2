package lt.bit.products.ui.service;

import java.util.List;
import java.util.UUID;
import lt.bit.products.ui.model.Supplier;

public interface SupplierService {

  List<Supplier> getSuppliers();

  Supplier getSupplier(UUID id);

  void saveSupplier(Supplier supplier);

  void deleteSupplier (UUID id);

}
