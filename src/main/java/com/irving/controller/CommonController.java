package com.irving.controller;

import com.irving.commonly.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/common")
@Controller
@Slf4j
public class CommonController {

    @Value("${my.path}")
    private String path;

    @ResponseBody
    @PostMapping("/upload")
    public R<?> upload(@RequestParam("file") MultipartFile multipartFile, HttpServletResponse response) {
        String s = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().indexOf("."));
        String paths = UUID.randomUUID().toString() + s;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            multipartFile.transferTo(new File(path+paths));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(paths);
    }

    @GetMapping("/download")
    public void download(@RequestParam("name") String name, HttpServletResponse response) {
        try {
            ServletOutputStream stream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(new File(path + name));
            byte[] byt = new byte[1024];
            int index = 0;
            response.setContentType("image/jpeg");
            while ((index = inputStream.read(byt)) != -1) {
                stream.write(byt, 0, index);
                stream.flush();
            }
            inputStream.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
