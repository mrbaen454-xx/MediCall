package com.example.doktor.Service;

import java.util.List;

import com.example.doktor.Payload.Req.DoktorPayloadReq;
import com.example.doktor.Payload.Res.DoktorPayloadRes;

public interface DoktorService {
    List<DoktorPayloadRes> getAllDoktor() throws Exception;
    public DoktorPayloadRes findById(DoktorPayloadReq doktor) throws Exception;


}
