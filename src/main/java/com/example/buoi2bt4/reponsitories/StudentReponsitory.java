package com.example.buoi2bt4.reponsitories;

import com.example.buoi2bt4.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentReponsitory extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s where s.city like lower(concat('%',:name,'%') ) or s.name like lower(concat('%',:name,'%') ) ")
    List<Student> findByCityAndName(String name );
}
