package com.cst438.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository <Student, Integer> {
	
	// declare the following method to return a single Student object
	// default JPA behavior that findBy methods return List<Student> except for findById.
//	@Query("select s from Register r where r.student.email=:email")
	public Student findByEmail(String email);
	@SuppressWarnings("unchecked")
	Student save(Student s);
	
	@Modifying
	@Query("update Student s set status_code = :code  where email = :email")
	int putHold(@Param("email") String email, @Param("code") int code);
	
	@Modifying
	@Query("update Student s set status_code = :code where email = :email")
	int releaseHold(@Param("email") String email, @Param("code") int code);
}
