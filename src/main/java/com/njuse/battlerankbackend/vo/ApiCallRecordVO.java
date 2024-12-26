package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.po.ApiCallRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallRecordVO {
    private String url;
    private String method;
    private Long count;

    public ApiCallRecord toPO(){
        return new ApiCallRecord(url, method);
    }
}