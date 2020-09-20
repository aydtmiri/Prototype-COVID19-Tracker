package com.example.contra.Model

import com.example.contra.Model.CoronaCase

interface FireBaseCoronaCase_Callback {
    fun finishedLoadingCase(case: CoronaCase)
    fun failedLoadingCase()
}