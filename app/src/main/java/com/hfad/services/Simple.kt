package com.hfad.services

class Simple {

    private val lists:ArrayList<String> = arrayListOf()

    fun sum(a: Int, b: Int): Int {
        val plus = a + b
        val c = plus - 3
        return c
    }

    fun list(): List<String> {
        lists.add("Ali")
        lists.add("Maxim")
        lists.add("30")
        return lists
    }
}

