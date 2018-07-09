package by.zhuk.sweeter.service;


import by.zhuk.sweeter.model.User;
import by.zhuk.sweeter.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UserService.class })
public class UserServiceTest {

    private final UserRepository userRepository = PowerMockito.mock(UserRepository.class);
    private final MailSender mailSender =PowerMockito.mock(MailSender.class);
    private final PasswordEncoder passwordEncoder=PowerMockito.mock(PasswordEncoder.class);
    private UserService userService= new UserService(userRepository,passwordEncoder,mailSender);

    @Before
    public void setUp()  {
        Mockito.reset(userRepository);
        Mockito.reset(mailSender);
        Mockito.reset(passwordEncoder);
    }

    @Test
    public void loadUserByUsernamePositiveTest() {
        User user = new User();
        user.setUsername("admin");

        when(userRepository.findByUsername("admin")).thenReturn(user);
        Assert.assertEquals(userService.loadUserByUsername("admin"),user);

    }
    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameUsernameNotFoundTest() {
        User user = new User();
        when(userRepository.findByUsername("admin")).thenReturn(null);
        Assert.assertEquals(userService.loadUserByUsername("admin"),user);
    }
    @Test
    public void addUserPositiveTest() throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        UserService spy = PowerMockito.spy(new UserService(userRepository,passwordEncoder,mailSender));
        when(userRepository.findByUsername("admin")).thenReturn(null);
        when(userRepository.findByUsername("save")).thenReturn(null);
        when(passwordEncoder.encode("1234")).thenReturn(null);
        doNothing().when(spy, "sendMessage",user);
        Assert.assertTrue(spy.addUser(user));
    }

//    @Test
//    public void activateUser() {
//    }
//
//    @Test
//    public void findAll() {
//    }
//
//    @Test
//    public void saveUser() {
//    }
//
//    @Test
//    public void updateProfile() {
//    }
}
