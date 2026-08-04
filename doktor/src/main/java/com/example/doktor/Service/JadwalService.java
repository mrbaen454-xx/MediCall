package com.example.doktor.Service;

import java.util.List;

import com.example.doktor.Payload.Req.JadwalPayloadReq;
import com.example.doktor.Payload.Res.JadwalPayloadRes;

public interface JadwalService {
    public List<JadwalPayloadRes> getByIdDoktor(Long idDoktor) throws Exception;
    public JadwalPayloadRes findById(JadwalPayloadReq jadwal) throws Exception;
}
