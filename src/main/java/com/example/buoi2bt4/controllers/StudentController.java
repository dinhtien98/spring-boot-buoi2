package com.example.buoi2bt4.controllers;

import com.example.buoi2bt4.models.Student;
import com.example.buoi2bt4.responses.ApiResponse;
import com.example.buoi2bt4.responses.StudentListResponse;
import com.example.buoi2bt4.responses.StudentResponse;
import com.example.buoi2bt4.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllStudents(){
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(studentService.getAllStudents())
                .status(HttpStatus.OK.value())
                .message("OK")
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getAllStudentList(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<StudentResponse> studentResponsePage = studentService.getStudentReponsitory1(pageRequest);
        int totalPages = studentResponsePage.getTotalPages();
        List<StudentResponse> studentResponseList = studentResponsePage.getContent();
        StudentListResponse studentListResponse = StudentListResponse
                .builder()
                .studentResonseList(studentResponseList)
                .totalPages(totalPages)
                .build();
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(studentListResponse)
                .status(HttpStatus.OK.value())
                .message("OK")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("validation failed")
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        Student std = studentService.saveStudent(student);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(std)
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long id) {
        Student std = studentService.getStudentById(id);
        if (std == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        studentService.deleteStudent(id);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(null)
                .message("Deleted Successfully")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable Long id,@Valid @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("validation failed")
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        Student std = studentService.updateStudent(id, student);
        if(std == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(std)
                .status(HttpStatus.OK.value())
                .message("Update Success")
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchStudent(@RequestParam String name) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(studentService.findByCityAndName(name))
                .message("Search Success")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
