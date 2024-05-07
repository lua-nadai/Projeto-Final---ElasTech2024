package com.soulcode.projetofinal.repositories;



import com.soulcode.projetofinal.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT p FROM Person p WHERE p.email = :email")
    Person findByEmail(String email);

    @Query("SELECT p FROM Person p WHERE p.name = :name")
    Person findByName(String name);

    @Query("SELECT p FROM Person p WHERE p.resetToken = :resetToken")
    Person findByResetToken(@Param("resetToken") String resetToken);
}