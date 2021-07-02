package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/* default scope of this Bean is "singleton" */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("delete FROM User u WHERE u.timeStamp < (:elapsed - 10000)")
    void deleteOffline(@Param("elapsed") Long elapsed);

//    User findOne(String userName);
    User findUserByUserName(String userName);
    User findByUserName(String userName);
//    String findUserByloggedIn();

}
