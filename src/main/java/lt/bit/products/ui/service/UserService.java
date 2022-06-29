package lt.bit.products.ui.service;

import java.util.List;
import lt.bit.products.ui.model.User;
import lt.bit.products.ui.service.domain.UserEntity;
import lt.bit.products.ui.service.domain.UserRepository;
import lt.bit.products.ui.service.domain.UserRole;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

@Service
@SessionAttributes("authenticated")
public class UserService {

  private final UserRepository repository;
  private final ModelMapper mapper;

  boolean authenticated;

  public UserService(UserRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public void login(String username, String password) {
    if (repository.existsByUsernameAndPasswordAndRole(username, password, UserRole.ADMIN)) {
      setAuthenticated(true);
    }
  }

  public void logout() {
    setAuthenticated(false);
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  private void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

  public List<User> getUsers() {
    List<UserEntity> users = repository.findAll();
    // @formatter:off
    return mapper.map(users, new TypeToken<List<User>>() {}.getType());
    // @formatter:on
  }
}
