package com.company;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@RestController

public class EcommerceController {

    @Autowired
    private FileProcessorService fileProcessorService;

    public static String HEADER = "shop,start_date,end_date";

    @PostMapping("/uploadFile")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("dateFilter") String dateFilter) {

        BufferedReader br = null;
        String line;
        List<ShopDetails> results = new ArrayList<>();
        InputStream is = null;
        try {
            if(file.getOriginalFilename().length() <= 0){
                throw new FileInputException("Please selecte a file");
            }
            is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            fileProcessorService.emptyAllIds();
            while ((line = br.readLine()) != null) {
                if(!line.equals(HEADER)){
                    ShopDetails shop = fileProcessorService.shopMapper(line,dateFilter);
                    if(null != shop){
                        results.add(shop);
                    }
                }
            }
            if(results.size() < 1){
                throw new FileInputException("Change your filter criteria");
            }
            if(fileProcessorService.isDuplicatePresent()){
                throw new FileInputException("Duplicate shop Id's present");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Object>(results,HttpStatus.OK);
    }


}
