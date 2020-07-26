package com.epam.drill

object Class1 {
    fun primitiveIntMethod(): Int {
        return 1
    }

    fun obj(fls:java.lang.Integer): Class2 {
        return Class2
    }

    fun obj2(): Class4 {
        return Class4("x")
    }


    fun cls(newClass: Class3): String {
        return newClass.method1()
    }

    fun primitiveIntMethod(intq: Int): Int {
        return 1
    }

    fun primitiveShortMethod(): Short {
        return 1
    }

    fun primitiveBooleanMethod(): Boolean {
        return true
    }

    fun stringMethod(): String {
        return "xxx"
    }

    fun byteArrayMethod(): ByteArray {
        return byteArrayOf()
    }

    fun intArrayMethod(): IntArray {
        return intArrayOf()
    }


    fun methodVoid() {
        println("xss")
    }
}