package lazybro.company.factorycrm.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER,
    ADMIN,
    MANAGER,
    ENGINEER,
    BOSS;

    @Override
    public String getAuthority() {
        return name();
    }
}
