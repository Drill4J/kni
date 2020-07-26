package com.epam.drill.kni

interface JvmtiAgent {

    fun agentOnLoad(options: String): Int
    fun agentOnUnload()

}