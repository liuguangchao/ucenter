package com.bracelet;

import com.alibaba.fastjson.JSON;
import com.bracelet.dto.SosDto;
import com.bracelet.util.PushUtil;

public class PushTest {
    public static void main(String[] args) {
        // PushRequest pushRequest = new PushRequest();
        // pushRequest.setAppKey(24620906L);
        // pushRequest.setTargetValue("");
        SosDto dto = new SosDto();
        dto.setLat("22.22222");
        dto.setLng("138.13838");
        dto.setTimestamp(System.currentTimeMillis());
        System.out.println(JSON.toJSONString(dto));

        PushUtil.push("2wsx", "66666666666", JSON.toJSONString(dto), "测试");
    }
}
