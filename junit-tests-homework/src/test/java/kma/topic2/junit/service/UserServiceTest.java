package kma.topic2.junit.service;

import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.validation.UserValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {
    @SpyBean
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @SpyBean
    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource({
            "fdfd1,fds,gfgd",
            "ababab1,fdbabs,gfbagd",
            "fdfvd1,fbads,gfbabgd",
            "fdfbgd1,543215,gffdgd",
    })
    public void createNewUserTest(String login, String password, String fullname){
        NewUser newUser = NewUser.builder().login(login).password(password).fullName(fullname).build();
        userService.createNewUser(newUser);
        assertTrue(userRepository.isLoginExists(login));
        Mockito.verify(userValidator).validateNewUser(newUser);
        Mockito.verify(userRepository).saveNewUser(newUser);

        User dbUser = userService.getUserByLogin(login);

        assertTrue(dbUser.getLogin().equals(login) &&
                            dbUser.getPassword().equals(password) &&
                            dbUser.getFullName().equals(fullname));

        Mockito.verify(userRepository).getUserByLogin(login);
    }

    @ParameterizedTest
    @CsvSource({
            "fdfd2,fds,gfgd",
            "ababab2,fdbabs,gfbagd",
            "fdfvd2,fbads,gfbabgd",
            "fdfbgd2,543215,gffdgd",
    })
    public void createNewUserExistTest(String login, String password, String fullname){
        NewUser newUser = NewUser.builder().login(login).password(password).fullName(fullname).build();
        userService.createNewUser(newUser);
        assertTrue(userRepository.isLoginExists(login));
        Mockito.verify(userValidator).validateNewUser(newUser);
        Mockito.verify(userRepository).saveNewUser(newUser);

        assertThatThrownBy(() -> userValidator.validateNewUser(newUser)).isInstanceOf(LoginExistsException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "fdfd3,fdfsdfdsfdss,gfgd",
            "ababab3,fs,gfbagd",
            "fdfvd3,fba%^$,gfbabgd",
            "fdfbgd3,54$.5-,gffdgd",
            "fdfbgd3,--DS-,gffdgd",
    })
    public void createNewUserWrongPasswordTest(String login, String password, String fullname){
        NewUser newUser = NewUser.builder().login(login).password(password).fullName(fullname).build();
        assertThatThrownBy(() -> userService.createNewUser(newUser)).isInstanceOf(ConstraintViolationException.class);
    }


}