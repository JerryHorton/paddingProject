package com.example.padding.controller;

import com.example.padding.common.R;
import com.example.padding.entity.School;
import com.example.padding.service.SchoolService;
import com.example.padding.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Autowired
    private SchoolService schoolService;

    @Value("${padding.path}")
    private String basePath;

    /**
     * 文件上传(批量添加学校信息)
     *
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile excelFile) {
        //使用POI解析表格数据
        List<String[]> excel = null;
        try {
            excel = POIUtils.readExcel(excelFile);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return R.error("上传失败");
        }
        //数据整合
        List<School> data = new ArrayList<>();
        for (String[] strings : excel) {
            if (strings[0].equals("")) {    //读取到空白行直接退出，默认数据全部扫描成功
                break;
            }
            Long id = Long.parseLong(strings[0]);
            Long provId = Long.parseLong(strings[1]);
            Long cityId = Long.parseLong(strings[2]);
            String name = strings[3];
            String address = strings[4];
            School school = new School();
            school.setId(id);
            school.setProvId(provId);
            school.setCityId(cityId);
            school.setName(name);
            school.setAddress(address);
            data.add(school);
        }
        schoolService.saveBatch(data);
        return R.success("上传成功");
    }

    /**
     * 文件下载(模版文件)
     *
     * @param filename
     * @param response
     */
    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response) {

        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + filename + ".xlsx"));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
