package com.shaoyuanhu.test.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author: ShaoYuanHu
 * @Description: 测试lambda表达式的写法
 * @Date: Create in 2018-01-30 15:04
 */
public class LambdaTest {

    /**
     * 使用lambda表达式取代匿名内部类
     */
    @Test
    public void runnable() {
        //老的开启线程方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("the old using");
            }
        }).start();

        //使用lambda表达式开启线程
        new Thread(() -> System.out.println("this is lambda function")).start();

        new Thread(() -> {
            int i = 0 ;
            while (i < 10) {
                System.out.println(++i);
            }
        }).start();
    }

    /**
     * 使用lambda表达式对集合进行迭代
     */
    @Test
    public void iterTest() {
        List<String> list = Arrays.asList("java", "php", "python", "lua", "ruby", "c#");

        //使用for循环遍历
        for (String s : list) {
            System.out.println(s);
        }

        //使用lambda表达式遍历
        list.forEach(x -> System.out.println(x));
        list.forEach(System.out::println);
    }

    /**
     * 用lambda表达式实现map
     */
    @Test
    public void mapTest() {
        List<String> list = Arrays.asList("java", "php", "python", "lua", "ruby", "c#");
        list.stream().map(x -> x+"-value").forEach(System.out::println);
    }

    /**
     * 用lambda表达式实现map与reduce
     */
    @Test
    public void mapReduceTest() {
        List<Double> list = Arrays.asList(10D,20D,30D,40D);

        //使用lambda表达式
        Double d = list.stream().map(x -> x + x * 0.05).reduce((sum, x) -> sum + x).get();
        System.out.println(d);

        //使用for循环
        double sum = 0;
        for (Double dou : list) {
            dou += dou * 0.05;
            sum += dou;
        }
        System.out.println(sum);
    }

    /**
     * 使用lambda实现filter操作，从集合中过滤掉一部分数据
     */
    @Test
    public void filterTest() {
        List<Double> list = Arrays.asList(10D,20D,30D,40D);
        List<Double> newList = list.stream().filter(x -> x > 25D).collect(Collectors.toList());
        newList.stream().forEach(x -> System.out.println(x));
    }

    /**
     * 使用lambda与函数式接口Predicate配合使用
     * Predicate很适合用于过滤集合中的数据。
     */
    @Test
    public void predicateTest() {
        List<String> list = Arrays.asList("java", "php", "python", "lua", "ruby", "c#");

        System.out.println("language starts with j : ");
        filterTest(list, x -> x.startsWith("j"));

        System.out.println("\nlanguage ends with a : ");
        filterTest(list, x -> x.endsWith("a"));

        System.out.println("\nall language : ");
        filterTest(list, x -> true);

        System.out.println("\nno language : ");
        filterTest(list, x -> false);

        System.out.println("\nlanguage length bigger three : ");
        filterTest(list, x -> x.length() > 3);


    }

    public void filterTest(List<String> language, Predicate<String> condition) {
        language.stream().filter(x -> condition.test(x)).forEach(x -> System.out.println(x));
    }

}
