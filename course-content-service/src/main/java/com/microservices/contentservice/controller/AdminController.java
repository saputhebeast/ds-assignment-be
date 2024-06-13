package com.microservices.contentservice.controller;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.service.ContentService;
import com.microservices.contentservice.core.type.ApprovalStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    @NonNull
    private final ContentService contentService;

    @GetMapping("awaiting-approval")
    public ResponseEntity<ResponseEntityDto> getAllContentsAwaitingApproval() {
        ResponseEntityDto response = contentService.getAllContentAwaitingApproval();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ResponseEntityDto> updateApprovalOfContent(@PathVariable String id, @RequestBody @NonNull ApprovalStatus approvalStatus) {
        ResponseEntityDto response = contentService.updateApprovalOfContent(id, approvalStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
