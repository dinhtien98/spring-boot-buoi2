package com.example.buoi2bt4.controllers;

import com.example.buoi2bt4.dto.StudentImageDTO;
import com.example.buoi2bt4.excepations.ResourceNotFoundException;
import com.example.buoi2bt4.models.Rating;
import com.example.buoi2bt4.models.Student;
import com.example.buoi2bt4.models.StudentImage;
import com.example.buoi2bt4.responses.ApiResponse;
import com.example.buoi2bt4.responses.StudentListResponse;
import com.example.buoi2bt4.responses.StudentResponse;
import com.example.buoi2bt4.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/search1")
    public ResponseEntity<ApiResponse> searchStudent1(@RequestParam int startYear,int endYear) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(studentService.findByBirthdayBetween(startYear,endYear))
                .message("Search Success")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search2")
    public ResponseEntity<ApiResponse> searchStudent2(
            @RequestParam(value = "rating", required = false) Rating rating,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "startYear", required = false) int startYear,
            @RequestParam(value = "endYear", required = false) int endYear) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(studentService.searchStudents(rating, name,city,startYear,endYear))
                .message("Search Success")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getAllImage/{id}")
    public ResponseEntity<ApiResponse> getAllImage(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(studentService.getStudentImages(id))
                .status(HttpStatus.OK.value())
                .message("Get All Image Success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/uploads/{id}")
    public ResponseEntity<ApiResponse>  uploads(@PathVariable Long id, @ModelAttribute("files") List<MultipartFile> files) throws IOException {
        List<StudentImage> studentImages = new ArrayList<>();
        int count = 0;
        for (MultipartFile file : files) {
            if (file != null) {
                if (file.getSize() == 0){
                    count++;
                    continue;
                }
            }
            String fileName = storeFile(file);
            StudentImageDTO studentImageDTO = StudentImageDTO.builder()
                    .imageUrl(fileName)
                    .build();
            StudentImage studentImage = studentService.saveStudentImage(id, studentImageDTO);
            if (studentImage.getStudent() == null) {
                throw new ResourceNotFoundException("Student khong tim thay voi id: " + id);
            }
            studentImages.add(studentImage);
        }


        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentImages)
                .message("Upload images successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        try{
            java.nio.file.Path imagePath = Paths.get("upload/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if(resource.exists()){
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            }else{
                return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new UrlResource(Paths.get("upload/notfound.jpeg").toUri()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString()+"_"+fileName;
        java.nio.file.Path uploadDdir= Paths.get("upload");
        if(!Files.exists(uploadDdir)) {
            Files.createDirectory(uploadDdir);
        }
        java.nio.file.Path destination=Paths.get(uploadDdir.toString(),uniqueFileName);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
