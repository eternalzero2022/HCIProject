package com.njuse.battlerankbackend.po;

import com.njuse.battlerankbackend.vo.ApiCallRecordVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallRecord {
    private String url;
    private String method;

    public ApiCallRecordVO toVO(){
        return new ApiCallRecordVO(url, method,0L);
    }

}
