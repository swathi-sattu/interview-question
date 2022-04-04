package com.example.demo.course.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
	@Query(value = "select * from TBL_COURSES where title= ?1", nativeQuery = true)
	List<CourseEntity> findByTitle(String title);
}
