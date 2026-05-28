package org.example.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.dto.ImportError;

public class JDBCUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/dtn2601_buoi2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "S1a2n3g4@2006";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Kết nối DB thất bại", e);
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static class CsvImporter {
        @FunctionalInterface
        public interface RowParser<T> {
            T parse(String line, String[] fields, List<String> errors) throws Exception;
        }

        @FunctionalInterface
        public interface BatchSaver<T> {
            boolean save(List<T> entities) throws Exception;
        }

        public static <T> String importFromCSV(
            String pathName,
            String errorFileHeader,
            RowParser<T> parser,
            BatchSaver<T> saver,
            String successMsgTemplate,
            String partialSuccessMsgTemplate,
            String dbErrorMsgTemplate
        ) {
            File file = new File(pathName);
            if (!file.exists() || !file.isFile()) {
                return "[Lỗi] File không tồn tại: " + pathName;
            }

            List<T> entities = new ArrayList<>();
            List<ImportError> importErrors = new ArrayList<>();
            boolean firstLine = true;

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    List<String> errors = new ArrayList<>();
                    String[] fields = line.split(",");

                    T entity = parser.parse(line, fields, errors);
                    if (errors.isEmpty() && entity != null) {
                        entities.add(entity);
                    } else {
                        importErrors.add(new ImportError(line, String.join(" | ", errors)));
                    }
                }
            } catch (Exception e) {
                return "[Lỗi] Không thể đọc file: " + e.getMessage();
            }

            String pathError;
            if (pathName.toLowerCase().endsWith(".csv")) {
                pathError = pathName.substring(0, pathName.length() - 4) + "_error.csv";
            } else {
                pathError = pathName + "_error.csv";
            }

            if (!importErrors.isEmpty()) {
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathError), "UTF-8"))) {
                    bw.write(errorFileHeader);
                    bw.newLine();
                    for (ImportError error : importErrors) {
                        bw.write(error.getLine() + "," + error.getMessage());
                        bw.newLine();
                    }
                } catch (Exception e) {
                    return "[Lỗi] Không thể ghi file lỗi: " + e.getMessage();
                }
            }

            boolean checkImport = false;
            if (!entities.isEmpty()) {
                try {
                    checkImport = saver.save(entities);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                checkImport = true;
            }

            if (importErrors.isEmpty()) {
                return String.format(successMsgTemplate, entities.size());
            } else {
                if (entities.isEmpty()) {
                    return "Import thất bại, toàn bộ " + importErrors.size() + " dòng đều bị lỗi. Chi tiết lỗi đã được xuất ra: " + pathError;
                }
                return checkImport
                    ? String.format(partialSuccessMsgTemplate, entities.size(), importErrors.size(), pathError)
                    : String.format(dbErrorMsgTemplate, pathError);
            }
        }
    }
}