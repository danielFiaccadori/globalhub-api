package com.globalhub.main.controller;

import com.globalhub.main.application.dto.teacher.TeacherDetailsDTO;
import com.globalhub.main.application.response.BaseResponse;
import com.globalhub.main.application.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Page<TeacherDetailsDTO>>> findAll(Pageable pageable) {
        Page<TeacherDetailsDTO> teachers = service.findAll(pageable);
        return ResponseEntity.ok().body(BaseResponse.ok(teachers));
    }

}