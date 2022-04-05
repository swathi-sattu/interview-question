package com.example.demo.course.persistence;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParticipantsRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	ParticipantsRepository userRepo;
	
	ParticipantEntity entity = new ParticipantEntity();
	
	@Before
	public void setUp() {
		
		entity.setCourseId(Long.valueOf(1));
		entity.setName("user1");
	}

	@Test
	public void testFindByName() {
		
		entity = entityManager.persist(entity);
		assertEquals(userRepo.findByName(Long.valueOf(1), "user1"), entity);
	}
	
	@Test
	public void testFindByCourseId() {
		
		entity = entityManager.persist(entity);
		List<ParticipantEntity>  list = userRepo.findByCourseId(Long.valueOf(1));
		assertEquals(list.get(0), entity);
	}
}
