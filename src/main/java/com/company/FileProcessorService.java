package com.company;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class FileProcessorService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter filterFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private List<String> allIds = new ArrayList<>();

    public <T> boolean isDuplicatePresent() {
        Set<String> set=new HashSet<>();
        Set<String> duplicateElements=new HashSet<>();

        for (String element : allIds) {
            if(!set.add(element)){
                duplicateElements.add(element);
            }
        }
        return (duplicateElements.size() > 0) ? true : false;
    }

    public ShopDetails shopMapper(String shopDetails,String dateFilter) throws FileInputException{
        ShopDetails shop = new ShopDetails();
        LocalDate filterDate = null;
        String[] shopArray = shopDetails.split(",");
        try {
            allIds.add(shopArray[0]);
            shop.setShopId(shopArray[0]);
            shop.setStartDate(shopArray[1]);
            shop.setEndDate(shopArray[2]);

            LocalDate startDate = LocalDate.parse(shopArray[1], formatter);
            LocalDate endDate = LocalDate.parse(shopArray[2], formatter);

            if (endDate.isBefore(startDate)) {
                throw new FileInputException("Start date must be before End date");
            }

            if(dateFilter.length() > 0){
                filterDate = LocalDate.parse(dateFilter, filterFormatter);
                if((filterDate.isAfter(startDate) && filterDate.isBefore(endDate)) || filterDate.isEqual(startDate) || filterDate.isEqual(endDate)){
                    shop = populateShopObject(shop,shopArray,startDate,endDate);
                }else{
                    shop = null;
                }
            }else{
                shop = populateShopObject(shop,shopArray,startDate,endDate);
            }

        }catch(DateTimeParseException ex){
            throw new FileInputException("Input date format is Invalid");
        }catch(FileInputException ex){
            throw new FileInputException(ex.getMsg());
        }catch (Exception ex){
            throw new FileInputException("Error in Uploading Data");
        }
        return shop;
    }

    public void emptyAllIds() {
        allIds = new ArrayList<>();
    }

    private ShopDetails populateShopObject(ShopDetails shop , String[] shopArray ,LocalDate startDate,LocalDate endDate){

        shop.setShopId(shopArray[0]);
        shop.setStartDate(startDate.format(displayFormatter));
        shop.setEndDate(endDate.format(displayFormatter));

        return shop;
    }
}
