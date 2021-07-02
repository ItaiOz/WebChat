package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{

        List<Message> findTop5ByOrderByIdDesc();
        List <Message> findByUserName(String userName);

        //custom query to find what messages including the entered keyword
        @Query("select m FROM Message m WHERE m.messageContent  LIKE %:word% ")
        List<Message> findMessageByWord(@Param("word") String word);

}
