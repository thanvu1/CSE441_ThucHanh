package com.example.fxrates.model

data class TyGia(
    val type: String,          // mã tiền tệ: USD, EUR…
    val imageUrl: String,      // link cờ
    val muaTienMat: String,    // Buy (Cash)
    val muaCK: String,         // Buy (TT/Transfer)
    val banTienMat: String,    // Sell (Cash)
    val banCK: String          // Sell (TT/Transfer)
)
