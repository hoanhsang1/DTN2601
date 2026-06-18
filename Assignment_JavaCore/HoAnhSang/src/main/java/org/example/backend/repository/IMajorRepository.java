package org.example.backend.repository;

import org.example.entity.Major;

import java.util.List;

// Interface định nghĩa các thao tác dữ liệu cho chuyên ngành
public interface IMajorRepository {

    // Lấy toàn bộ danh sách chuyên ngành
    List<Major> findAll();
}
