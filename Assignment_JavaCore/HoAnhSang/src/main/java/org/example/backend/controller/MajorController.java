package org.example.backend.controller;

import org.example.backend.service.IMajorService;
import org.example.backend.service.impl.MajorServiceImpl;
import org.example.entity.Major;

import java.util.List;

// Điều phối yêu cầu từ tầng Frontend xuống MajorService
public class MajorController {

    private final IMajorService majorService = new MajorServiceImpl();

    // Lấy danh sách toàn bộ chuyên ngành
    public List<Major> getAllMajors() {
        return majorService.getAllMajors();
    }
}
