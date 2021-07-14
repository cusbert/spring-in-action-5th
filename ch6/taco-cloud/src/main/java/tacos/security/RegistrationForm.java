package tacos.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.domain.User;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        // PasswordEncoder를 객체를 사용해서 비밀번호를 암호화 한 후 User 리턴
        return new User(
                username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phone);
    }
}
