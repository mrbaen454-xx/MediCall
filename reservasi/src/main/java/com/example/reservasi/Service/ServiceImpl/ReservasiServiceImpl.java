package com.example.reservasi.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.reservasi.Entity.ReservasiEntity;
import com.example.reservasi.Payload.Req.EmailPayloadReq;
import com.example.reservasi.Payload.Req.JadwalPayloadReq;
import com.example.reservasi.Payload.Req.ReservasiPayloadReq;
import com.example.reservasi.Payload.Res.DoktorPayloadRes;
import com.example.reservasi.Payload.Res.JadwalPayloadRes;
import com.example.reservasi.Repository.ReservasiRepository;
import com.example.reservasi.Service.ReservasiService;


@Service
public class ReservasiServiceImpl implements ReservasiService {
    

    @Autowired
    private ReservasiRepository reservasiRepository;

     @Value("${smtp.host}")
    private String smtpHost;
    
    @Value("${smtp.port}")
    private String smtpPort;

    @Override
    public JadwalPayloadReq setJadwal(ReservasiPayloadReq reservasiPayloadReq) {
        JadwalPayloadReq jadwalPayloadReq = new JadwalPayloadReq();
        jadwalPayloadReq.setIdJadwal(reservasiPayloadReq.getIdJadwal());
        jadwalPayloadReq.setIdDoktor(reservasiPayloadReq.getIdDoktor());
        return jadwalPayloadReq;
    }

    @Override
    public void saveReservasi(JadwalPayloadRes res,DoktorPayloadRes DoktorRes, ReservasiPayloadReq req)throws Exception {
       try {
        if (res.getStatus().equals("AVAILABLE")) {
            throw new Exception("jadwal sudah terisi");
        }
        else
        {
            ReservasiEntity reservasiEntity = new ReservasiEntity();
            reservasiEntity.setNamaPasien(req.getNamaPasien());
            reservasiEntity.setEmailPasien(req.getEmailPasien());
            reservasiEntity.setIdDoktor(req.getIdDoktor());
            reservasiEntity.setIdJadwal(req.getIdJadwal()); 
            reservasiRepository.save(reservasiEntity);

            EmailPayloadReq payload = new EmailPayloadReq();
            payload.setSenderEmail("msaroni454@gmail.com");
            payload.setSenderPassword("ruoytfbdawxujvth");
            payload.setReceiverEmailTo(req.getEmailPasien());
            payload.setEmailSubject("Reservasi Berhasil");
            payload.setBody(
                    "<html>" +
                            "<body>" +
                            "Hallo " + req.getNamaPasien() + ",<br><br>" +
                            "Reservasi konsultasi anda berhasil.<br><br>" +
                            "Doktor : " + DoktorRes.getNamaDoktor() + "<br>" +
                            "Spesialis : " + DoktorRes.getSpesialisasi() + "<br>" +
                            "Tanggal : " + res.getTanggal() + "<br>" +
                            "Jam : " + res.getJam() + "<br><br>" +
                            "Terimakasih!!" +
                            "</body>" +
                            "</html>");
             sendEmail(payload);
        }
       } catch (Exception e) {
        throw e;
       }
        
    }
    @Override
    @Scheduled(cron = "0 0 8 * * *")
    public void scheduledEmail() {
        List<ReservasiEntity> reservasiEntity = reservasiRepository.findAll();
        if (!reservasiEntity.isEmpty()) {
            for (ReservasiEntity entity : reservasiEntity) {
                EmailPayloadReq payload = new EmailPayloadReq();
                payload.setSenderEmail("msaroni454@gmail.com");
                payload.setSenderPassword("ruoytfbdawxujvth");
                payload.setReceiverEmailTo(entity.getEmailPasien());
                payload.setEmailSubject("Schedule Reservasi");
                payload.setBody(
                        "<html>" +
                                "<body>" +
                                "Hallo " + entity.getNamaPasien() + ",<br><br>" +
                                "Jangan Lupa Konsultasi anda" + "<br><br>" +
                                "Terimakasih!!" +
                                "</body>" +
                                "</html>");
                try {
                    sendEmail(payload);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void sendEmail(EmailPayloadReq payload) throws Exception {
        try {
           
            InternetAddress[] senderEmailWithoutName = InternetAddress.parse(payload.getSenderEmail());

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    senderEmailWithoutName[0].getAddress(),
                                    payload.getSenderPassword());
                        }
                    });

            try {

                // format waktu
                LocalDateTime currDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currDateTime.format(formatter);

                // buat message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(payload.getSenderEmail()));

                // TO
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(payload.getReceiverEmailTo()));

                // CC optional
                if (payload.getReceiverEmailCc() != null &&
                        !payload.getReceiverEmailCc().isEmpty()) {

                    message.setRecipients(
                            Message.RecipientType.CC,
                            InternetAddress.parse(payload.getReceiverEmailCc()));
                }

                // BCC optional
                if (payload.getReceiverEmailBcc() != null &&
                        !payload.getReceiverEmailBcc().isEmpty()) {

                    message.setRecipients(
                            Message.RecipientType.BCC,
                            InternetAddress.parse(payload.getReceiverEmailBcc()));
                }

                // subject
                message.setSubject("PUB Mailing System - "
                        + formattedDateTime + " - "
                        + payload.getEmailSubject());

                // body
                String emailBody = "";
                if (payload.getBody() != null) {
                    emailBody = payload.getBody().toString();
                }

                message.setContent(emailBody, "text/html; charset=utf-8");
                // kirim email
                Transport.send(message);

                System.out.println("Email berhasil dikirim");

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Gagal proses message email");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Gagal membuat session email");
        }
    }
}
