package com.xkcoding.email.controller;

import com.xkcoding.email.dto.OrderExcelBuyerDTO;
import com.xkcoding.email.util.ExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:cyw
 * @CreateTime: 2021/1/4 21:06
 **/
@RequestMapping("/excel")
@RestController
public class ExcelController {

    @GetMapping("/exportAllOrder")
    public void exportAllOrder(HttpServletResponse response
    ) {

        List<OrderExcelBuyerDTO> list = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            list.add(new OrderExcelBuyerDTO(i,"卖方"+i,i));
        }
        ExcelUtil.writeExcelToResp(response, "order-buyer-" + 1, "sheet1", list, OrderExcelBuyerDTO.class);
    }

}





























