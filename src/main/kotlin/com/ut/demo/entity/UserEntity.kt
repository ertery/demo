package com.ut.demo.entity

import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(

        @Column
        val password: String? = null,

        @Column
        val username: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    @OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)])
    val records: List<RecordEntity> = mutableListOf()
}