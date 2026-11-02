package com.hema.studentmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hema.studentmanagement.controller.StudentController;
import com.hema.studentmanagement.entity.Student;
import com.hema.studentmanagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .build();
    }

    @Test
    void saveStudent_shouldReturnStudent() throws Exception {

        setup();

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentService.saveStudent(any(Student.class)))
                .thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hema"))
                .andExpect(jsonPath("$.email").value("hema@gmail.com"))
                .andExpect(jsonPath("$.course").value("Java"));

        verify(studentService, times(1))
                .saveStudent(any(Student.class));
    }

    @Test
    void getAllStudents_shouldReturnStudents() throws Exception {

        setup();

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentService.getAllStudents())
                .thenReturn(Arrays.asList(student));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hema"))
                .andExpect(jsonPath("$[0].email").value("hema@gmail.com"))
                .andExpect(jsonPath("$[0].course").value("Java"));

        verify(studentService, times(1))
                .getAllStudents();
    }

    @Test
    void getStudentById_shouldReturnStudent() throws Exception {

        setup();

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentService.getStudentById(1L))
                .thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hema"))
                .andExpect(jsonPath("$.email").value("hema@gmail.com"))
                .andExpect(jsonPath("$.course").value("Java"));

        verify(studentService, times(1))
                .getStudentById(1L);
    }

    @Test
    void updateStudent_shouldReturnUpdatedStudent() throws Exception {

        setup();

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentService.updateStudent(eq(1L), any(Student.class)))
                .thenReturn(student);

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hema"))
                .andExpect(jsonPath("$.email").value("hema@gmail.com"))
                .andExpect(jsonPath("$.course").value("Java"));

        verify(studentService, times(1))
                .updateStudent(eq(1L), any(Student.class));
    }

    @Test
    void deleteStudent_shouldReturnSuccessMessage() throws Exception {

        setup();

        doNothing().when(studentService)
                .deleteStudent(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Student deleted successfully!"));

        verify(studentService, times(1))
                .deleteStudent(1L);
    }

    @Test
    void getStudentsByCourse_shouldReturnStudents() throws Exception {

        setup();

        Student student = new Student();
        student.setName("Hema");
        student.setEmail("hema@gmail.com");
        student.setCourse("Java");

        when(studentService.getStudentsByCourse("Java"))
                .thenReturn(Arrays.asList(student));

        mockMvc.perform(get("/students/course/Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hema"))
                .andExpect(jsonPath("$[0].email").value("hema@gmail.com"))
                .andExpect(jsonPath("$[0].course").value("Java"));

        verify(studentService, times(1))
                .getStudentsByCourse("Java");
    }
}
