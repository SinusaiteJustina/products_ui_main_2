package lt.bit.products.ui.service.error;


import lt.bit.products.ui.model.User;
import lt.bit.products.ui.model.UserProfile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static java.util.Objects.isNull;
import static org.aspectj.util.LangUtil.isEmpty;


@Component
public class UserValidator {

    public void validate(User user) throws ValidationException {
        validatePasswords(user.getPassword(), user.getConfirmedPassword(), user.getId() == null);
        validateFields(user);

    }

    private void validatePasswords(String newPassword, String confirmedPassword, boolean newUser)
            throws ValidationException {
        if (StringUtils.hasLength(newPassword) && !newPassword.equals(confirmedPassword)) {
            throw new ValidationException(ErrorCode.NOT_SAME_PASSWORDS);
        } else if (newUser && !StringUtils.hasLength(newPassword)) {
            throw new ValidationException(ErrorCode.PASSWORD_IS_REQUIRED);
        }
    }

    private void validateFields(User user) throws ValidationException {
        if(isEmpty(user.getUsername()) || isNull(user.getRole()) || isNull(user.getStatus())) {
            throw new ValidationException(ErrorCode.MANDATORY_FIELDS);
        }
    }

    public void validate(UserProfile userProfile) throws ValidationException {
        validateFields(userProfile);

    }
    private void validateFields(UserProfile userProfile) throws ValidationException {
        if(userProfile.getName().isBlank()
                || userProfile.getAddress().isBlank()
                || userProfile.getEmail().isBlank()
                || userProfile.getCountry().isBlank()
                || userProfile.getCity().isBlank()
                || userProfile.getPhone().isBlank()) {
            throw new ValidationException(ErrorCode.MANDATORY_FIELDS);
        }
    }

}
