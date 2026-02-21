package com.globalhub.main.controller;

import com.globalhub.main.application.dto.course.CourseCreationRequestDTO;
import com.globalhub.main.application.dto.course.CourseDetailsDTO;
import com.globalhub.main.application.dto.course.UpdateCourseRequestDTO;
import com.globalhub.main.application.response.BaseResponse;
import com.globalhub.main.application.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Page<CourseDetailsDTO>>> findAll(Pageable pageable) {
        Page<CourseDetailsDTO> courses = service.findAll(pageable);
        return ResponseEntity.ok().body(BaseResponse.ok(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CourseDetailsDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(BaseResponse.ok(service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<CourseDetailsDTO>> createCourse(@RequestBody @Valid CourseCreationRequestDTO courseCreationRequestData) {
        return ResponseEntity.ok(BaseResponse.ok(service.createCourse(courseCreationRequestData)));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<CourseDetailsDTO>> updateCourse(@PathVariable Long id,
                                                                       @RequestBody @Valid UpdateCourseRequestDTO courseUpdateRequestData) {
        return ResponseEntity.ok(BaseResponse.ok(service.updateCourseDetails(id, courseUpdateRequestData)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> deactivateCourse(@PathVariable Long id) {
        service.updateActivity(id, false);
        return ResponseEntity.ok().body(BaseResponse.ok(null, "Course successfully deactivated ;("));
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> activateCourse(@PathVariable Long id) {
        service.updateActivity(id, true);
        return ResponseEntity.ok().body(BaseResponse.ok(null, "Course successfully reactivated :)"));
    }

}
