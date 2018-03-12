package com.ut.demo.entity

import com.ut.demo.enums.LoggingLevel
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "records")
data class RecordEntity(

        val dt: LocalDateTime,

        @Enumerated(EnumType.STRING)
        val level: LoggingLevel,

        val message: String,

        val author: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
}