package br.com.libdolf.cinemamanager.entities.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Table(name = "tb_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    @Getter
    public enum Values{
        ADMIN(1L),
        MODERATOR(2L),
        USER(3L);

        final long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }
}