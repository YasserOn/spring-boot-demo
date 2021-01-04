package com.xkcoding.email.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderExcelBuyerDTO {
    @NumberFormat("#.####")
    @ColumnWidth(25)
    @ExcelProperty(value = "订单编号", index = 1)
    private Long id;

    @ColumnWidth(40)
    @ExcelProperty(value = "卖方", index = 2)
    private String supplierName;

    @ExcelProperty(value = "订单金额", index = 3)
    @ColumnWidth(15)
    @NumberFormat("#.####")
    private Long totalPrice;


}
