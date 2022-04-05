package com.example.demo.course.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepository extends JpaRepository<ParticipantEntity, Long> {
	
	@Query(value = "select * from TBL_PARTICIPANTS where course_id= ?1 AND name= ?2", nativeQuery = true)
	ParticipantEntity findByName(Long courseId, String name);

	@Query(value = "select * from TBL_PARTICIPANTS where course_id= ?1", nativeQuery = true)
    List<ParticipantEntity> findByCourseId(long id);

}
