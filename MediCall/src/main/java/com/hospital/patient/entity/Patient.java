package com.hospital.patient.entity;

import com.hospital.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE patients SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
public class Patient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "medical_record_number", nullable = false, unique = true, length = 20)
    private String medicalRecordNumber;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 16)
    private String nik;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup;

    @Column(name = "bpjs_number", length = 20)
    private String bpjsNumber;

    @Column(length = 50)
    private String status;

    @Column(name = "photo_url")
    private String photoUrl;
}
