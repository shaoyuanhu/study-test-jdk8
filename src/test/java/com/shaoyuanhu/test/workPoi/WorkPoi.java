package com.shaoyuanhu.test.workPoi;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: ShaoYuanHu
 * @Description: 读取本地考勤文件，将分析结果输出到指定文件中
 * @Date: Create in 2018-02-01 09:48
 */
public class WorkPoi {

    /**
     * 所有人本月的考勤数据
     */
    private static HashMap<String, List<WorkTime>> allData;
    /** 需要统计考勤的所有人 */
    //private static String[] allPeople = {"刘杰", "张开伟", "王宏建", "雷思琦", "徐鑫", "庄春旭", "赵子光", "姚亚鹏", "冀湘元", "苏雨然", "杨波", "王文波", "肖艳芳", "方明飞", "崔景源", "关宇威", "季鹏宇", "张成", "宋婧", "赵雅轩", "侯星男", "曹名瑞", "王磊", "吴倍宗", "张佳男", "庄智玲", "王凤英", "韩星"};
    /**
     * 需要统计考勤的所有人，包含领导
     */
    //private static String[] allPeople = {"刘杰", "冀湘予", "张开伟", "李金钟", "王宏建", "雷思琦", "刘伟", "徐鑫", "庄春旭", "赵子光", "姚亚鹏", "冀湘元", "苏雨然", "杨波", "王文波", "王永永", "肖艳芳", "方明飞", "崔景源", "关宇威", "季鹏宇", "潘晓锋", "张成", "张志成", "赵雅轩", "侯星男", "曹名瑞", "王磊", "吴倍宗", "张立琳", "张佳男", "庄智玲", "王凤英", "韩星"};
    private static String[] allPeople = {"刘杰", "冀湘予", "张开伟", "李金钟", "王宏建", "雷思琦", "刘伟", "徐鑫", "庄春旭", "赵子光", "姚亚鹏", "冀湘元", "苏雨然", "杨波", "王文波", "王永永", "肖艳芳", "方明飞", "崔景源", "关宇威", "季鹏宇", "潘晓锋", "张成", "宋婧", "赵雅轩", "侯星男", "曹名瑞", "王磊", "吴倍宗", "张立琳", "张佳男", "庄智玲", "王凤英", "韩星", "陈永泽"};

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");

    static {
        allData = new HashMap<>(32);
        List<String> list = Arrays.asList(allPeople);
        list.forEach(x -> {
            ArrayList<WorkTime> arrayList = new ArrayList<>(32);
            allData.put(x, arrayList);
        });
    }

    public static void main(String[] args) {
        //输出当前路径
        //System.out.println(new File(".").getAbsolutePath());
        try (FileInputStream fileInputStream = new FileInputStream("./workExcel/2018.1.xlsx");
             FileOutputStream fileOutputStream = new FileOutputStream("./workExcel/2018.1总结.xlsx");
        ) {
            boolean readSuccess = readExcel(fileInputStream);
            if (readSuccess) {
                XSSFWorkbook result = createExcel();
                if (result != null) {
                    result.write(fileOutputStream);
                }
                //打印考勤信息到控制台
                System.out.println("总人数： " + allData.size());

                allData.forEach((k,v) -> System.out.println("姓名：" + k + "，考勤信息：" + JsonUtil.toJson(v)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static XSSFWorkbook createExcel() {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            XSSFSheet sheet = wb.createSheet("考勤总结");
            // 设置表格默认列宽度为10字节
            sheet.setDefaultColumnWidth(10);
            int rowNum = 0;
            for (Map.Entry<String, List<WorkTime>> entry : allData.entrySet()) {
                //姓名行
                XSSFRow nameRow = sheet.createRow(rowNum++);
                nameRow.createCell(0).setCellValue("姓名");
                nameRow.createCell(1).setCellValue(entry.getKey());

                //按照日期排序
                List<WorkTime> list = entry.getValue();
                Collections.sort(list, (w1,w2) -> (int)(w1.getToday().getTime()-w2.getToday().getTime()));

                //日期行
                XSSFRow dateRow = sheet.createRow(rowNum++);
                dateRow.createCell(0).setCellValue("日期");
                //签到时间行
                XSSFRow beginTimeRow = sheet.createRow(rowNum++);
                beginTimeRow.createCell(0).setCellValue("签到时间");
                //签退时间行
                XSSFRow endTimeRow = sheet.createRow(rowNum++);
                endTimeRow.createCell(0).setCellValue("签退时间");
                //工作时长行
                XSSFRow workTimeRow = sheet.createRow(rowNum++);
                workTimeRow.createCell(0).setCellValue("工作时长");
                for (int x = 0; x < list.size(); x++) {
                    dateRow.createCell(x+1).setCellValue(sdf1.format(list.get(x).getToday()));

                    String beginTime = "";
                    try {
                        beginTime = sdf2.format(list.get(x).getBeginTime());
                    }catch (Exception e) {
                        //e.printStackTrace();
                    }
                    beginTimeRow.createCell(x+1).setCellValue(beginTime);

                    String endTime = "";
                    try {
                        endTime = sdf2.format(list.get(x).getEndTime());
                    }catch (Exception e) {
                        //e.printStackTrace();
                    }
                    endTimeRow.createCell(x+1).setCellValue(endTime);

                    workTimeRow.createCell(x+1).setCellValue(list.get(x).getWorkTime());
                }

                //空行
                sheet.createRow(rowNum++);
                sheet.createRow(rowNum++);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return wb;
    }

    public static boolean readExcel(InputStream inputStream) {
        try {
            XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
            int numberOfSheets = xwb.getNumberOfSheets();
            for (int k = 1; k < numberOfSheets; k++) {
                XSSFSheet sheet = xwb.getSheetAt(k);
                XSSFRow row = null;
                XSSFCell cell = null;
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int i = 2; i < physicalNumberOfRows; i++) {
                    row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    WorkTime workTime = new WorkTime();
                    for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                        cell = row.getCell(j);
                        if (cell == null) {
                            continue;
                        }
                        String value = cell.toString();
                        if (value == null || "".equals(value)) {
                            continue;
                        }
                        switch (j) {
                            case 1:
                                workTime.setName(value);
                                break;
                            case 2:
                                workTime.setToday(sdf1.parse(value));
                                break;
                            case 3:
                                workTime.setBeginTime(sdf2.parse(value));
                                break;
                            case 4:
                                workTime.setEndTime(sdf2.parse(value));
                                break;
                            case 5:
                                try {
                                    Date da = cell.getDateCellValue();
                                    String format = sdf3.format(da);
                                    workTime.setWorkTime(format);
                                    break;
                                }catch (Exception e) {
                                    //e.printStackTrace();
                                }
                                workTime.setWorkTime(value);
                                break;
                        }
                    }
                    if (workTime.getName()!=null) {
                        List<WorkTime> workTimes = allData.get(workTime.getName());
                        if (workTimes==null) {
                            System.out.println(workTime.getName());
                            List<WorkTime> list = new ArrayList<>();
                            list.add(workTime);
                            allData.put(workTime.getName(),list);
                        }else {
                            workTimes.add(workTime);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
