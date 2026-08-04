package com.example.reservasi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.reservasi.Payload.Req.JadwalPayloadReq;
import com.example.reservasi.Payload.Req.ReservasiPayloadReq;
import com.example.reservasi.Payload.Res.DoktorPayloadRes;
import com.example.reservasi.Payload.Res.JadwalPayloadRes;
import com.example.reservasi.Service.ReservasiService;

@Controller
@RequestMapping("/reservasi")
public class ReservasiController {

    @Autowired
    private ReservasiService reservasiService;
    

   private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8099").build();

   @PostMapping("/add")
   public ResponseEntity<?> addReservasi(@RequestBody ReservasiPayloadReq req) {
    try {
        JadwalPayloadReq jadwalPayloadReq = reservasiService.setJadwal(req);
        JadwalPayloadRes jadwalRes = webClient.post()
                .uri("/doktor/getByIdJadwal").bodyValue(jadwalPayloadReq)
                .retrieve()
                .bodyToMono(JadwalPayloadRes.class)
                .block();
        DoktorPayloadRes doktorRes  = webClient.post()
                .uri("/doktor/getByIdDoktor").bodyValue(jadwalPayloadReq)
                .retrieve()
                .bodyToMono(DoktorPayloadRes.class)
                .block();

        reservasiService.saveReservasi(jadwalRes, doktorRes, req);

        return new ResponseEntity<>("Reservasi Berhasil", HttpStatus.OK);

    } catch (Exception e) {
        System.out.println("Pesan error: " + e.getMessage());
        System.out.println("Penyebab error: " + e.getCause());
        if (e.getCause() != null && e.getCause().toString().contains("Connection refused")) {
            return new ResponseEntity<>("koneksi ke service gagal", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>("Gagal karena lain hal", HttpStatus.NOT_FOUND);
    }
       
   }

}
