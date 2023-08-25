package cinema_springboot.service.impl;

import com.example.cinema_springboot.model.entity.Cinema;
import com.example.cinema_springboot.model.entity.Role;
import com.example.cinema_springboot.model.entity.User;
import com.example.cinema_springboot.repository.RoleRepository;
import com.example.cinema_springboot.repository.UserRepository;
import com.example.cinema_springboot.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
//@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements ServiceLayer<User> {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private JavaMailSender emailSender;
    final PasswordEncoder passwordEncoder;

    final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(User user) {

//        List<Role> roles = new ArrayList<>();
//        Role role = new Role();
//        role.setName("USER");
//        roles.add(role);
//        user.setRoles(roles);
        Role role = roleRepository.findByName("USER");

        if (role == null) {
            role = new Role("USER");
        }
        user.setRoles(new ArrayList<>(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<Cinema> findAll() {
        return null;
    }

    @Override
    public User update(Long id, User user) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    public void sendMessage(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom("gadgetarium_application@gmail.com");
        mailMessage.setSubject("Order information!");
        String massage = "This message from my Spring Boot application";
        mailMessage.setText(massage);
        emailSender.send((mailMessage));
    }
}
