package com.moscue.parser;

import com.moscue.entity.Stock;

/**
 * Created by Aaron on 14-3-29.
 */
public class StockDataParser {
    public static Stock parser(String str) {
        Stock stock = new Stock();
        int eIdx = str.indexOf('=');
        if (eIdx < 1) {
            return null;
        }
        String s = str.substring(0, eIdx);
        int idx2 = s.lastIndexOf('_');
        if (idx2 < 1) {
            return null;
        }
        String num = s.substring(idx2+1, eIdx);
        stock.setCode(num);
        int beginIndex = str.indexOf("\"");
        int endIdx = str.lastIndexOf("\"");
        String arr[] = str.substring(beginIndex, endIdx).split(",");
        if (arr.length >= 32){
            stock.setName(arr[0].substring(1, arr[0].length()));
            stock.setOpenPrice(Double.parseDouble(arr[1]));
            stock.setClosePrice(Double.parseDouble(arr[2]));
            stock.setCurrentPrice(Double.parseDouble(arr[3]));
            stock.setMaxPrice(Double.parseDouble(arr[4]));
            stock.setMinPrice(Double.parseDouble(arr[5]));
            stock.setBuy1(Double.parseDouble(arr[6]));
            stock.setSell1(Double.parseDouble(arr[7]));
            stock.setTradingVolume(Integer.parseInt(arr[8]));
            stock.setTradingAmount(Double.parseDouble(arr[9]));
            stock.setBuy1Declare(Integer.parseInt(arr[10]));
            stock.setBuy1Price(Double.parseDouble(arr[11]));
            stock.setBuy2Declare(Integer.parseInt(arr[12]));
            stock.setBuy2Price(Double.parseDouble(arr[13]));
            stock.setBuy3Declare(Integer.parseInt(arr[14]));
            stock.setBuy3Price(Double.parseDouble(arr[15]));
            stock.setBuy4Declare(Integer.parseInt(arr[16]));
            stock.setBuy4Price(Double.parseDouble(arr[17]));
            stock.setBuy5Declare(Integer.parseInt(arr[18]));
            stock.setBuy5Price(Double.parseDouble(arr[19]));
            stock.setSell1Declare(Integer.parseInt(arr[20]));
            stock.setSell1Price(Double.parseDouble(arr[21]));
            stock.setSell2Declare(Integer.parseInt(arr[22]));
            stock.setSell2Price(Double.parseDouble(arr[23]));
            stock.setSell3Declare(Integer.parseInt(arr[24]));
            stock.setSell3Price(Double.parseDouble(arr[25]));
            stock.setSell4Declare(Integer.parseInt(arr[26]));
            stock.setSell4Price(Double.parseDouble(arr[27]));
            stock.setSell5Declare(Integer.parseInt(arr[28]));
            stock.setSell5Price(Double.parseDouble(arr[29]));
            stock.setDate(arr[30]);
            stock.setTime(arr[31]);
        }
        return stock;
    }
}
