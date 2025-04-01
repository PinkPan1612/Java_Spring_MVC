package vn.rawuy.laptopshop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadService {
    private final ServletContext servletContext;

    public UploadService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        // don't save file
        if (file.isEmpty()) {
            return "";
        }
        // relative path: absolute Path
        String rootPath = this.servletContext.getRealPath("/resources/images");
        String finalName = "";
        try {
            byte[] bytes = file.getBytes();// lấy dữ liệu dưới dạng byte
            // tìm nơi lưu file( trỏ tới thư mục avatar),
            // sparator là dấu /
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists()) // kiểm tra tồn tại
                dir.mkdirs(); // nếu ko tồn tại thì tạo mới(make directory)

            // tạo mới tên file = thời gian hiện tại + tên file gốc
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return finalName;
    }
}
