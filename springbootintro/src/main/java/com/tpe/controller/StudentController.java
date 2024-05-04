package com.tpe.controller;
import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
        ***** SORU-1 :  @Controller yerine , @Component kullanirsam ne olur ??
        **    CEVAP-1 : Dispatcher , @Controller ile annote edilmis sınıfları tarar ve
        bunların içindeki @RequestMapping annotationlari algilamaya calisir. Dikkat :
        @Component ile annote edilen siniflar taranmayacaktir..
        Ayrica  @RequestMapping'i yalnızca sınıfları @Controller ile annote edilmis olan
        methodlar üzerinde/içinde kullanabiliriz ve @Component, @Service, @Repository vb.
        ile ÇALIŞMAZ…
        ***** SORU-2 : @RestController ile @Controller arasindaki fark nedir ??
        **   CEVAP-2 : @Controller, Spring MVC framework'ünün bir parçasıdır.genellikle HTML
        sayfalarının görüntülenmesi veya yönlendirilmesi gibi işlevleri gerçekleştirmek
        üzere kullanılır.
                       @RestController annotation'ı, @Controller'dan türetilmiştir ve RESTful
         web servisleri sağlamak için kullanılır.Bir sınıfın üzerine konulduğunda, tüm
         metodlarının HTTP taleplerine JSON gibi formatlarda cevap vermesini sağlar.
         ***** SORU-3 : Controller'dan direk Repo ya gecebilir miyim
         **   CEVAP-3: HAYIR, BusinessLogic ( kontrol ) katmani olan Service'i atlamam gerekir.
 */

@RestController
@RequestMapping("/students")// http://localhost:8080/students + GET + POST + PUT + DELETE
public class StudentController {
    @Autowired
    private StudentService studentService;


    //Not: getAll() ******************************************************
    @GetMapping // http://localhost:8080/students + GET
    // 1 --> Student[] olur mu ? Olmaz List<> ile calismamam gerekiyor
    // 2 --> Response icinde Status codunu rahat setlemek icin ResponseEntity ..
    public ResponseEntity<List<Student>> getAll(){
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students); //200 status kodu ile Student nesnelerini client tarafına yönlendirdi.
        //return new ResponseEntity<>(students, HttpStatus.OK);
    }

    //Not: CreateStudent()  *************************************************
    @PostMapping // http://localhost:8080/students + POST + JSON
    public ResponseEntity<Map<String, String>> createStudent(@Valid @RequestBody Student student){ // JSON --> Student

        // @Valid : parametreler valid mi kontrol eder, bu örenekte Student
        //objesi oluşturmak için  gönderilen fieldlar yani
        //name gibi özellikler düzgün set edilmiş mi ona bakar.
        // @RequestBody = gelen  requestin bodysindeki bilgiyi ,
        //Student objesine map edilmesini sağlıyor.


        studentService.createStudent(student);
        Map<String,String> map = new HashMap<>();
        map.put("message", "Student is created successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    // Not : Get Student BY ID with RequestParam *******************************************
    @GetMapping("/query") // http://localhost:8081/students/query?id=1&name=Ahmet  + GET
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student = studentService.findStudent(id);//200
        return ResponseEntity.ok(student);
    }

    //Not : Get Student by ID with PathVariable *********************************************
    @GetMapping("/{id}") //http://localhost:8081/students/1
    public ResponseEntity<Student> getStudentByPath(@PathVariable("id") Long id){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    // !!! TRICK = 1 data alacaksam PathVariable ama birden fazla data alacaksam
    //  RequestParam daha kullanisli

    //Not: Delete Student  *******************************
    @DeleteMapping("/{id}")//http://localhost:8081/students/1 + DELETE
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        Map<String,String> map = new HashMap<>();
        map.put("message", "Student was deleted successfully");
        map.put("status", "true");
        //return new ResponseEntity<>(map, HttpStatus.OK);
        return ResponseEntity.ok(map);
    }

    //Not: Update Student  *********************************
    @PutMapping("/{id}")//http://loclahost:8081/students/1 + PUT + JSON //RequestParam ile de değişkenler gönderilebilir
    // ama güvanlik ve adres satırının uzun olması sebebiyle tercih edilmez.
    public ResponseEntity<String> updateStudent(@PathVariable Long id,
                                                @RequestBody Student student){

    }

}