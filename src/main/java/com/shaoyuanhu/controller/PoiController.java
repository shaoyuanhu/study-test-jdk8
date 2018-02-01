package com.shaoyuanhu.controller;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.shaoyuanhu.workPoi.WorkPoi.createExcel;
import static com.shaoyuanhu.workPoi.WorkPoi.readExcel;

/**
 * @Author: ShaoYuanHu
 * @Description:
 * @Date: Create in 2018-02-01 14:31
 */
@RestController
@RequestMapping("/poi")
public class PoiController {

    @RequestMapping("/create")
    public void create() {
        //		System.out.println(new File(".").getAbsolutePath());
        try (FileInputStream fileInputStream = new FileInputStream("./workExcel/2018.1.xlsx");
             FileOutputStream fileOutputStream = new FileOutputStream("./workExcel/2018.1总结.xlsx");
        ) {
            boolean readSuccess = readExcel(fileInputStream);
            if (readSuccess) {
                XSSFWorkbook result = createExcel();
                if (result != null) {
                    result.write(fileOutputStream);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
