package HealthAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "clients")
public class Client extends User {
    @Column(nullable = false)
    @Pattern(regexp ="^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$", message = "Invalid date.")
    private SimpleDateFormat birthDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^(?:9[1-36][0-9]|2[12][0-9]|23[1-689]|24[1-59]|25[1-9]|26[1-35689]|27[1-9]|28[1-69]|29[1256]|30[0-9])[0-9]{6}$", message = "Invalid NIF.")
    private int NIF;
    @Column(nullable = false)
    private Address address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
