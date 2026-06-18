package org.example.backend.service.impl;

import org.example.backend.repository.IMajorRepository;
import org.example.backend.repository.impl.MajorRepositoryImpl;
import org.example.backend.service.IMajorService;
import org.example.entity.Major;

import java.util.List;

// Hiện thực IMajorService, gọi xuống MajorRepository
public class MajorServiceImpl implements IMajorService {

    private final IMajorRepository majorRepository = new MajorRepositoryImpl();

    // Lấy toàn bộ danh sách chuyên ngành
    @Override
    public List<Major> getAllMajors() {
        return majorRepository.findAll();
    }
}
