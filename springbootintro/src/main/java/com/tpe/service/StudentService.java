package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

//Not: getAll() ************************************************
    public List<Student> getAll() {
        //!!!handle etmemiz gereken bir durum var mı? YOK
        return studentRepository.findAll(); // SELECT * FROM student
    }

    public void createStudent(Student student) {

        //!!! kontrol etmemiz gereken bir durum var mi? Email unique mi kontrolü
        if(studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictException("Email is already exist");
        }
        studentRepository.save(student);
    }
}
