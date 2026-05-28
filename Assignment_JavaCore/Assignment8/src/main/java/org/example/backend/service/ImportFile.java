package org.example.backend.service;

import org.example.dto.ImportError;

import java.util.List;

public interface ImportFile<T> {

    /**
     * Đọc file CSV, parse từng dòng thành đối tượng T hợp lệ.
     * Các dòng lỗi được ghi vào importErrors.
     */
    List<T> readFile(String path, List<ImportError> importErrors);

    /**
     * Validate một dòng CSV (đã tách thành mảng fields).
     * Trả về đối tượng T nếu hợp lệ, null nếu có lỗi.
     * Các lỗi được thêm vào danh sách errors.
     */
    T validate(String[] fields, List<String> errors);

    /**
     * Lưu danh sách thực thể hợp lệ vào database (batch insert).
     */
    boolean insertEntitiesToDB(List<T> entities) throws Exception;

    /**
     * Xuất danh sách lỗi ra file CSV cạnh file gốc (_error.csv).
     */
    void exportFileError(String pathName, List<ImportError> importErrors) throws Exception;

    /**
     * Phương thức tổng hợp: đọc file -> validate -> insert -> xuất lỗi.
     * Trả về chuỗi thông báo kết quả.
     */
    default String importFile(String path) {
        List<ImportError> importErrors = new java.util.ArrayList<>();
        List<T> entities = readFile(path, importErrors);

        String pathError = path.toLowerCase().endsWith(".csv")
            ? path.substring(0, path.length() - 4) + "_error.csv"
            : path + "_error.csv";

        if (!importErrors.isEmpty()) {
            try {
                exportFileError(pathError, importErrors);
            } catch (Exception e) {
                return "[Lỗi] Không thể ghi file lỗi: " + e.getMessage();
            }
        }

        boolean success = false;
        if (!entities.isEmpty()) {
            try {
                success = insertEntitiesToDB(entities);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            success = true;
        }

        if (importErrors.isEmpty()) {
            return "Import thành công. Đã nhập " + entities.size() + " bản ghi.";
        } else if (entities.isEmpty()) {
            return "Import thất bại, toàn bộ " + importErrors.size()
                + " dòng đều bị lỗi. Chi tiết lỗi đã được xuất ra: " + pathError;
        } else {
            return success
                ? "Import hoàn tất (có lỗi). Đã nhập " + entities.size()
                    + " bản ghi. " + importErrors.size()
                    + " dòng bị lỗi (đã xuất file lỗi tại " + pathError + ")."
                : "Lỗi kết nối cơ sở dữ liệu khi import. Chi tiết lỗi đã được xuất ra: " + pathError;
        }
    }
}
