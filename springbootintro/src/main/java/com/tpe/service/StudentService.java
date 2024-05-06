package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.hibernate.event.internal.PostDeleteEventListenerStandardImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Student findStudent(Long id) {
        return studentRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Student not found with id : " + id));
    }


    public void deleteStudent(Long id) {
        Student foundStudent = findStudent(id);
        //ikisi de aynı performans aynı
        //studentRepository.delete(foundStudent);
        studentRepository.deleteById(id);
    }


    //Not: Update Student *************************************************
    public void updateStudent(Long id, StudentDTO studentDTO) {
        //!!! id'li ogrenci var mi kontrolu :
        Student student = findStudent(id);
        // !!! email exist mi ? ve eger email degisecek ise DB de mevcutta olan emaillerden olmamasi gerekiyor
        boolean emailExist = studentRepository.existsByEmail(studentDTO.getEmail());

        if(emailExist && !studentDTO.getEmail().equals(student.getEmail())){
            throw new ConflictException("Email is already exist");
        }

        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        // !!! TRICK : asagidaki save ile beraber ogrencinin mevcut id bilgisi degisir mi ??
        // CEVAP : id degismez...
        studentRepository.save(student);
    }

    //Not: Pageable ******************************************
    public Page<Student> getAllWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    //Not: Get By Lastname  **************************
    public List<Student> findStudent(String lastName){
        return studentRepository.findByLastName(lastName);
    }


    //Not: Get All Student By Grade ( JPQL ) Java Persistence Query Language
    public List<Student> findAllEqualsGrade(Integer grade) {
        return studentRepository.findAllEqualsGrade(grade);//JPQL
    }

    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findStudentDTOById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id : " + id));
    }
}
