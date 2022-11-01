package com.cst438.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://registerf-cst438.herokuapp.com/"})
public class StudentController {
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	GradebookService gradebookService;
	
	@GetMapping("/student")
	public Student getStudent( @RequestParam("email") String email) {
		System.out.println("/student called.");
		String student_email = email;   // student's email 
		
		Student student = studentRepository.findByEmail(student_email);
		System.out.println(student + " " + email);
		if (student != null) {
			return student;
		} else {
			System.out.println("/student not found. "+student_email);
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student not found. " );
		}
	}
	@PostMapping("/student")
	@Transactional
	public Student addStudent(@RequestBody Student student){
		String s_email = student.getEmail();
		Student findStudent = studentRepository.findByEmail(s_email);
		if(findStudent==null) {
			Student s = new Student();
			s.setEmail(student.getEmail());
			s.setName(student.getName());
			s.setStatusCode(student.getStatusCode());
//			s.setStudent_id(student.getStudent_id());
			studentRepository.save(s);
			return student;
		}
		else {
			System.out.println("/student already in database. "+s_email);
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student already in database. " );
		}
		
		
	}
	@PutMapping("/Puthold/{email}")
	@Transactional
	public void putHold(@PathVariable String email){
		Student findStudent = studentRepository.findByEmail(email);
		if(findStudent!=null) {
			studentRepository.putHold(email,1);
			System.out.println("set code");
		}
		else {
			System.out.println("/student email not in database. "+email);
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student not in database. " );
		}
	}
	@PutMapping("/Releasehold/{email}")
	@Transactional
	public void releaseHold(@PathVariable String email){
		Student findStudent = studentRepository.findByEmail(email);
		if(findStudent!=null) {
			studentRepository.releaseHold(email, 0);
			System.out.println("set code");
		}
		else {
			System.out.println("/student email not in database. "+email);
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student not in database. " );
		}
	}
//	public Student helper(Student s) {
//		s.setEmail(s.getEmail());
//		s.setName(s.getName());
//		
//		if(s.getStatus()==null)
//		
//		return s
//	}

}
