package com.example.reservasi.Service;

import com.example.reservasi.Payload.Req.EmailPayloadReq;
import com.example.reservasi.Payload.Req.JadwalPayloadReq;
import com.example.reservasi.Payload.Req.ReservasiPayloadReq;
import com.example.reservasi.Payload.Res.DoktorPayloadRes;
import com.example.reservasi.Payload.Res.JadwalPayloadRes;

public interface ReservasiService {
    public JadwalPayloadReq setJadwal(ReservasiPayloadReq reservasiPayloadReq);
    public void saveReservasi (JadwalPayloadRes JadwalRes, DoktorPayloadRes DoktorRes,ReservasiPayloadReq req)throws Exception;
    public void scheduledEmail();
    public void sendEmail(EmailPayloadReq payload) throws Exception;


}
