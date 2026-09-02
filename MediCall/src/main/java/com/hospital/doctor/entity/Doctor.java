package com.hospital.doctor.entity;

import com.hospital.auth.entity.User;
import com.hospital.common.entity.BaseEntity;
import com.hospital.department.entity.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE doctors SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 16)
    private String nik;

    @Column(nullable = false, unique = true, length = 50)
    private String sip;

    @Column(nullable = false, unique = true, length = 50)
    private String str;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 50)
    private String status;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}
