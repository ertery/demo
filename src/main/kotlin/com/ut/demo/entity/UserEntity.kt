package com.ut.demo.entity

import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(

        @Column
        val password: String,

        @Column
        val username: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    @OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)])
    val records: List<RecordEntity> = mutableListOf()
}