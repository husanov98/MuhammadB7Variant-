package uz.pdp.muhammadb7variant2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.muhammadb7variant2.entity.Role;
import uz.pdp.muhammadb7variant2.entity.enums.RoleName;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Set<Role> findByRoleName(RoleName roleUser);
}
