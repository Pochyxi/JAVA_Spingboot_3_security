package com.adiener.security3.repository;

import com.adiener.security3.models.Role;
import com.adiener.security3.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    List<User> findAllByRole( Role role);

    void deleteByUsername(String username);

    @Query(
            value = "select c from User c where upper(c.lastName) like upper(concat('%', :lastname, "
                    + "'%'))"
    )
    List<User> getUserByLastnameContains( @Param( "lastname" ) String lastname );

}
