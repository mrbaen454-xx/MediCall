package com.example.doktor.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.doktor.Entity.DoktorEntity;
import com.example.doktor.Payload.Req.DoktorPayloadReq;
import com.example.doktor.Payload.Res.DoktorPayloadRes;
import com.example.doktor.Repository.DoktorRepository;
import com.example.doktor.Service.DoktorService;

@Service

public class DoktorServiceImpl implements DoktorService {
    @Autowired
    DoktorRepository doktorRepository;

    @Override
    public List<DoktorPayloadRes> getAllDoktor() throws Exception {
        List<DoktorPayloadRes> payloadRes = new ArrayList<>();
        try {
            List<DoktorEntity> doktor = doktorRepository.findAll();

            if (doktor.isEmpty()) {
                throw new Exception("Data Doktor Kosong");
            }
            for (DoktorEntity dok : doktor) {
                DoktorPayloadRes res = new DoktorPayloadRes();
                res.setIdDoktor(dok.getIdDoktor());
                res.setNamaDoktor(dok.getNamaDoktor());
                res.setSpesialisasi(dok.getSpesialisasi());
                payloadRes.add(res);
            }
            return payloadRes;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DoktorPayloadRes findById(DoktorPayloadReq doktor) throws Exception {
        try {
            DoktorEntity doktorEntity = doktorRepository.findById(doktor.getIdDoktor()).get();
            DoktorPayloadRes res = new DoktorPayloadRes();
            res.setIdDoktor(doktorEntity.getIdDoktor());
            res.setNamaDoktor(doktorEntity.getNamaDoktor());
            res.setSpesialisasi(doktorEntity.getSpesialisasi());
            return res;
        } catch (Exception e) {
            throw e;
        }
    }


}
