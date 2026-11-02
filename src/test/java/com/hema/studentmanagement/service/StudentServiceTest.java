package com.hema.studentmanagement.service;

import com.hema.studentmanagement.entity.Student;
import com.hema.studentmanagement.exception.ResourceNotFoundException;
import com.hema.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void saveStudent_shouldSaveStudent() {

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.saveStudent(student);

        assertNotNull(result);
        assertEquals("Hema", result.getName());
        assertEquals("hema@gmail.com", result.getEmail());
        assertEquals("Java", result.getCourse());

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void getAllStudents_shouldReturnStudents() {

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentRepository.findAll())
                .thenReturn(Arrays.asList(student));

        var result = studentService.getAllStudents();

        assertEquals(1, result.size());
        assertEquals("Hema", result.get(0).getName());

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_shouldReturnStudent() {

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Hema", result.getName());

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void getStudentById_shouldThrowExceptionWhenNotFound() {

        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> studentService.getStudentById(99L)
        );

        verify(studentRepository, times(1)).findById(99L);
    }

    @Test
    void updateStudent_shouldUpdateStudent() {

        Student existingStudent = new Student();
        existingStudent.setName("Old Name");
        existingStudent.setEmail("old@gmail.com");
        existingStudent.setCourse("Python");

        Student updatedStudent = new Student();
        updatedStudent.setName("Hema");
        updatedStudent.setEmail("hema@gmail.com");
        updatedStudent.setCourse("Java");

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(existingStudent));

        when(studentRepository.save(existingStudent))
                .thenReturn(existingStudent);

        Student result = studentService.updateStudent(1L, updatedStudent);

        assertEquals("Hema", result.getName());
        assertEquals("hema@gmail.com", result.getEmail());
        assertEquals("Java", result.getCourse());

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void deleteStudent_shouldDeleteStudent() {

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void getStudentsByCourse_shouldReturnStudents() {

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentRepository.findByCourse("Java"))
                .thenReturn(Arrays.asList(student));

        var result = studentService.getStudentsByCourse("Java");

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getCourse());

        verify(studentRepository, times(1))
                .findByCourse("Java");
    }
}

