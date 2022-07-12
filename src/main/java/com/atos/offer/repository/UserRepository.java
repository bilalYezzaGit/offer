package com.atos.offer.repository;

import com.atos.offer.model.User;
import com.atos.offer.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

}
