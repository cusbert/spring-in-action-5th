package tacos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.
        SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    // Spring Security 의 UserDetails 구현

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "USERNAME", length = 100, nullable = false)
    private final String username;
    @Column(name = "PASSWORD", length = 255, nullable = false)
    private final String password;
    @Column(name = "FULLNAME", length = 100)
    private final String fullname;
    @Column(name = "STREET")
    private final String street;
    @Column(name = "CITY")
    private final String city;
    @Column(name = "STATE")
    private final String state;
    @Column(name = "ZIP")
    private final String zip;
    @Column(name = "PHONE_NUMBER")
    private final String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Authority> authority;

    // 해당 사용자에게  부여된 권한을 저장한 컬렉션 반환환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
