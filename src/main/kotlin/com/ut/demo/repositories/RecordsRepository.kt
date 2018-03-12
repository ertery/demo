package com.ut.demo.repositories

import com.ut.demo.entity.RecordEntity
import org.springframework.data.jpa.repository.JpaRepository


interface RecordsRepository : JpaRepository<RecordEntity, Long> {

}