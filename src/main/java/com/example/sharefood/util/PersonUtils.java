package com.example.sharefood.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonUtils {

    /*获取字符串中的数字并生成一个LinkedList数组，便于数据的增加和删除*/
    public static LinkedList<Integer> getNumberInStr(String s){
        LinkedList<Integer> list=new LinkedList<Integer>();
        Pattern p = Pattern.compile("\\d{1,}");
        Matcher m = p.matcher(s);
        while(m.find()) {
            list.add(Integer.parseInt(m.group()));
        }
        return list;
    }
}
