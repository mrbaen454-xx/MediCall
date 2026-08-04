package com.example.doktor.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.doktor.Payload.Req.DoktorPayloadReq;
import com.example.doktor.Payload.Req.JadwalPayloadReq;
import com.example.doktor.Payload.Res.DoktorPayloadRes;
import com.example.doktor.Payload.Res.JadwalPayloadRes;
import com.example.doktor.Service.DoktorService;
import com.example.doktor.Service.JadwalService;
import com.example.doktor.Utility.Message;

@Controller
@RequestMapping("/doktor")
public class DoktorController {

    @Autowired
    private DoktorService doktorService;
    @Autowired 
    private JadwalService jadwalService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllDoktor() throws Exception {
        try {
            List<DoktorPayloadRes> payloadRes = doktorService.getAllDoktor();

            return new ResponseEntity<>(payloadRes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Terjadi error: " + e.getMessage());
            return new Message().error("Terjadi error: " + e.getMessage(), 500);
        }
    }

    @GetMapping("/jadwal")
    public ResponseEntity<?> getJadwalDoktor(@RequestParam Long id) throws Exception {
        try {
            List<JadwalPayloadRes> payloadRes = jadwalService.getByIdDoktor(id); ;
            return new ResponseEntity<>(payloadRes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Terjadi error: " + e.getMessage());
            return new Message().error("Terjadi error: " + e.getMessage(), 500);
        }
        
    }

   @PostMapping("/getByIdJadwal")
   public ResponseEntity<?> getByIdJadwal(@RequestBody JadwalPayloadReq payloadReq) throws Exception {
       try {
        JadwalPayloadRes payloadRes = jadwalService.findById(payloadReq);
        return new ResponseEntity<>(payloadRes, HttpStatus.OK);
       } catch (Exception e) {
           System.out.println("Terjadi error: " + e.getMessage());
           return new Message().error("Terjadi error: " + e.getMessage(), 500);
       }
   }
   @PostMapping("/getByIdDoktor")
   public ResponseEntity<?> getByIdDoktor(@RequestBody DoktorPayloadReq payloadReq) throws Exception {
       try {
       DoktorPayloadRes payloadRes = doktorService.findById(payloadReq);
        return new ResponseEntity<>(payloadRes, HttpStatus.OK);
       } catch (Exception e) {
           System.out.println("Terjadi error: " + e.getMessage());
           return new Message().error("Terjadi error: " + e.getMessage(), 500);
       }
   }


   
    
}
