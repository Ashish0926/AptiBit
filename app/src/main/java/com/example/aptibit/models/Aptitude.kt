package com.example.aptibit.models

data class Aptitude(
        var id: String = "",
        var title: String = "",
        var questions: MutableMap<String, Question> = mutableMapOf(),
)