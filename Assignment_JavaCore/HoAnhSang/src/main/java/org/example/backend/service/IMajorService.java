package org.example.backend.service;

import org.example.entity.Major;

import java.util.List;

// Interface định nghĩa các nghiệp vụ liên quan đến chuyên ngành
public interface IMajorService {

    // Lấy toàn bộ danh sách chuyên ngành
    List<Major> getAllMajors();
}
