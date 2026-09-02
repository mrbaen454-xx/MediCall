package com.hospital.prescription.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @Column(name = "drug_name", nullable = false, length = 255)
    private String drugName;

    @Column(nullable = false, length = 100)
    private String dosage;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
