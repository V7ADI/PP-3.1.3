package springboot.SpringBoot.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor // создаем пустой конструктор
@AllArgsConstructor //создаем конструктор включающего все возможные поля
@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(role, role1.role) && Objects.equals(users, role1.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, users);
    }
}


