package com.example.doktor.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.doktor.Entity.JadwalEntity;
import com.example.doktor.Payload.Req.JadwalPayloadReq;
import com.example.doktor.Payload.Res.JadwalPayloadRes;
import com.example.doktor.Repository.JadwalRepository;
import com.example.doktor.Service.JadwalService;

@Service
public class JadwalServiceImpl implements JadwalService {
    @Autowired
    JadwalRepository jadwalRepository;

    @Override
    public List<JadwalPayloadRes> getByIdDoktor(Long idDoktor) throws Exception {
        List<JadwalPayloadRes> payloadRes = new ArrayList<>();;
        try {
           List<JadwalEntity> jadwal = jadwalRepository.findByIdDoktor(idDoktor);
            if (jadwal.isEmpty()) {
                throw new Exception("Data Jadwal Kosong");
            }
            for (JadwalEntity jad : jadwal) {
                JadwalPayloadRes res = new JadwalPayloadRes();
                res.setIdJadwal(jad.getIdJadwal());
                res.setIdDoktor(jad.getIdDoktor());
                res.setTanggal(jad.getTanggal());
                res.setJam(jad.getJam());
                res.setStatus(jad.getStatus());
                payloadRes.add(res);
            }
            return payloadRes;

            
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public JadwalPayloadRes findById(JadwalPayloadReq jadwal) throws Exception
    {
        try {
            JadwalEntity jadwalEntity = jadwalRepository.findById(jadwal.getIdJadwal()).get();
            if (jadwalEntity.getStatus().equals("AVAILABLE")) {

                jadwalEntity.setStatus("UNAVAILABLE");
                jadwalRepository.save(jadwalEntity);
                
                JadwalPayloadRes res = new JadwalPayloadRes();
                res.setIdJadwal(jadwalEntity.getIdJadwal());
                res.setIdDoktor(jadwalEntity.getIdDoktor());
                res.setTanggal(jadwalEntity.getTanggal());
                res.setJam(jadwalEntity.getJam());
                res.setStatus(jadwalEntity.getStatus());
                return res;
            }
            else
            {
                throw new Exception("Jadwal TIdak Bisa DIPesan");
            }
        } catch (Exception e) {
            throw e;
        }
    } 
}
