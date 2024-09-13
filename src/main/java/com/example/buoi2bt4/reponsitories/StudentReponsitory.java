package com.example.buoi2bt4.reponsitories;

import com.example.buoi2bt4.models.Rating;
import com.example.buoi2bt4.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentReponsitory extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s where s.city like lower(concat('%',:name,'%') ) or s.name like lower(concat('%',:name,'%') ) ")
    List<Student> findByCityAndName(String name );

    @Query("SELECT s from Student s where year(s.birthday) between :startYear and :endYear")
    List<Student> findByBirthdayBetween(int startYear, int endYear);

    @Query("select s from Student s where "+
            "(:rating is null or s.rating = :rating) and "+
            "(:name is null or s.name like %:name%) and "+
            "(:city is null or s.city like %:city%) and "+
            "(:startYear is null or year(s.birthday) >= :startYear) and "+
            "(:endYear is null or year(s.birthday) <= :endYear)")
    List<Student> searchStudents(
            @Param("rating") Rating rating,
            @Param("name") String name,
            @Param("city") String city,
            @Param("startYear") int startYear,
            @Param("endYear") int endYear
    );
}
