package com.journalia_nitt.journalia_admin_cms

data class AdminDashBoardInfo(
    val token:String="",
    val author:String="SW Office",
    val title:String="Hostel Fee Payment",
    val description:String="",
    val deadline:String="7 Aug 2025",
    val file_url:String?="",
    val mode: Int=0,
    val link1: String="",
    val link2: String=""
)