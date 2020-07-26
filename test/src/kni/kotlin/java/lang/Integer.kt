@file:Suppress(
  "UnusedImport",
  "RemoveRedundantQualifierName",
  "UNUSED_VARIABLE",
  "unused",
  "UNUSED_PARAMETER",
  "UNNECESSARY_NOT_NULL_ASSERTION",
  "USELESS_CAST"
)

package java.lang

import com.epam.drill.jvmapi.gen.jclass
import com.epam.drill.jvmapi.gen.jfieldID
import com.epam.drill.jvmapi.gen.jmethodID
import com.epam.drill.jvmapi.gen.jobject
import com.epam.drill.jvmapi.readBytes
import kotlin.Byte
import kotlin.Double
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.Short
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned

open class Integer {
  private var objectRef: jobject

  constructor(arg0: Int? = null) {
    objectRef = com.epam.drill.jvmapi.gen.NewObject(classRef, initRef1263150, arg0)!!
  }

  constructor(arg0: String? = null) {
    objectRef = com.epam.drill.jvmapi.gen.NewObject(classRef, initRef1428966913, arg0)!!
  }

  constructor(jb: jobject) {
    objectRef = jb
  }

  fun self(): jobject = objectRef

  fun byteValue(): Byte {
    val methodResult = com.epam.drill.jvmapi.gen.CallByteMethod(objectRef, byteValueRef39777)!!
    return methodResult
  }

  fun shortValue(): Short {
    val methodResult = com.epam.drill.jvmapi.gen.CallShortMethod(objectRef, shortValueRef39794)!!
    return methodResult
  }

  fun intValue(): Int {
    val methodResult = com.epam.drill.jvmapi.gen.CallIntMethod(objectRef, intValueRef39784)!!
    return methodResult
  }

  fun longValue(): Long {
    val methodResult = com.epam.drill.jvmapi.gen.CallLongMethod(objectRef, longValueRef39785)!!
    return methodResult
  }

  fun floatValue(): Float {
    val methodResult = com.epam.drill.jvmapi.gen.CallFloatMethod(objectRef, floatValueRef39781)!!
    return methodResult
  }

  fun doubleValue(): Double {
    val methodResult = com.epam.drill.jvmapi.gen.CallDoubleMethod(objectRef, doubleValueRef39779)!!
    return methodResult
  }

  companion object {
    private val classRef: jclass

    var selfMethodId: jfieldID? = null

    private val toStringRef332472661: jmethodID

    private val toUnsignedStringRef332472661: jmethodID

    private val toHexStringRef1200754738: jmethodID

    private val toOctalStringRef1200754738: jmethodID

    private val toBinaryStringRef1200754738: jmethodID

    private val toUnsignedString0Ref332472661: jmethodID

    private val formatUnsignedIntRef2125035248: jmethodID

    private val toStringRef1200754738: jmethodID

    private val toUnsignedStringRef1200754738: jmethodID

    private val getCharsRef994436643: jmethodID

    private val stringSizeRef1263137: jmethodID

    private val parseIntRef1348271913: jmethodID

    private val parseIntRef1428966926: jmethodID

    private val parseUnsignedIntRef1348271913: jmethodID

    private val parseUnsignedIntRef1428966926: jmethodID

    private val valueOfRef308039925: jmethodID

    private val valueOfRef77435034: jmethodID

    private val valueOfRef1638725077: jmethodID

    private val initRef1263150: jmethodID

    private val initRef1428966913: jmethodID

    private val byteValueRef39777: jmethodID

    private val shortValueRef39794: jmethodID

    private val intValueRef39784: jmethodID

    private val longValueRef39785: jmethodID

    private val floatValueRef39781: jmethodID

    private val doubleValueRef39779: jmethodID

    private val toStringRef1942950347: jmethodID

    private val hashCodeRef39784: jmethodID

    private val hashCodeRef1263137: jmethodID

    private val equalsRef1565685781: jmethodID

    private val getIntegerRef77435034: jmethodID

    private val getIntegerRef308039925: jmethodID

    private val getIntegerRef461314879: jmethodID

    private val decodeRef77435034: jmethodID

    private val compareToRef1688332075: jmethodID

    private val compareRef39187080: jmethodID

    private val compareUnsignedRef39187080: jmethodID

    private val toUnsignedLongRef1263138: jmethodID

    private val divideUnsignedRef39187080: jmethodID

    private val remainderUnsignedRef39187080: jmethodID

    private val highestOneBitRef1263137: jmethodID

    private val lowestOneBitRef1263137: jmethodID

    private val numberOfLeadingZerosRef1263137: jmethodID

    private val numberOfTrailingZerosRef1263137: jmethodID

    private val bitCountRef1263137: jmethodID

    private val rotateLeftRef39187080: jmethodID

    private val rotateRightRef39187080: jmethodID

    private val reverseRef1263137: jmethodID

    private val signumRef1263137: jmethodID

    private val reverseBytesRef1263137: jmethodID

    private val sumRef39187080: jmethodID

    private val maxRef39187080: jmethodID

    private val minRef39187080: jmethodID

    private val compareToRef1565685764: jmethodID

    init {
      classRef = com.epam.drill.jvmapi.gen.FindClass("java/lang/Integer")!!
      toStringRef332472661 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toString",
          "(II)Ljava/lang/String;")!!
      toUnsignedStringRef332472661 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "toUnsignedString", "(II)Ljava/lang/String;")!!
      toHexStringRef1200754738 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toHexString",
          "(I)Ljava/lang/String;")!!
      toOctalStringRef1200754738 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toOctalString",
          "(I)Ljava/lang/String;")!!
      toBinaryStringRef1200754738 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "toBinaryString", "(I)Ljava/lang/String;")!!
      toUnsignedString0Ref332472661 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "toUnsignedString0", "(II)Ljava/lang/String;")!!
      formatUnsignedIntRef2125035248 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "formatUnsignedInt", "(II[CII)I")!!
      toStringRef1200754738 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toString",
          "(I)Ljava/lang/String;")!!
      toUnsignedStringRef1200754738 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "toUnsignedString", "(I)Ljava/lang/String;")!!
      getCharsRef994436643 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "getChars",
          "(II[C)V")!!
      stringSizeRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "stringSize", "(I)I")!!
      parseIntRef1348271913 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "parseInt",
          "(Ljava/lang/String;I)I")!!
      parseIntRef1428966926 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "parseInt",
          "(Ljava/lang/String;)I")!!
      parseUnsignedIntRef1348271913 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "parseUnsignedInt", "(Ljava/lang/String;I)I")!!
      parseUnsignedIntRef1428966926 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "parseUnsignedInt", "(Ljava/lang/String;)I")!!
      valueOfRef308039925 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "valueOf",
          "(Ljava/lang/String;I)Ljava/lang/Integer;")!!
      valueOfRef77435034 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "valueOf",
          "(Ljava/lang/String;)Ljava/lang/Integer;")!!
      valueOfRef1638725077 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "valueOf",
          "(I)Ljava/lang/Integer;")!!
      initRef1263150 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>", "(I)V")!!
      initRef1428966913 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>",
          "(Ljava/lang/String;)V")!!
      byteValueRef39777 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "byteValue", "()B")!!
      shortValueRef39794 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "shortValue", "()S")!!
      intValueRef39784 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "intValue", "()I")!!
      longValueRef39785 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "longValue", "()J")!!
      floatValueRef39781 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "floatValue", "()F")!!
      doubleValueRef39779 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "doubleValue", "()D")!!
      toStringRef1942950347 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toString",
          "()Ljava/lang/String;")!!
      hashCodeRef39784 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "hashCode", "()I")!!
      hashCodeRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "hashCode", "(I)I")!!
      equalsRef1565685781 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "equals",
          "(Ljava/lang/Object;)Z")!!
      getIntegerRef77435034 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "getInteger",
          "(Ljava/lang/String;)Ljava/lang/Integer;")!!
      getIntegerRef308039925 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "getInteger",
          "(Ljava/lang/String;I)Ljava/lang/Integer;")!!
      getIntegerRef461314879 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "getInteger",
          "(Ljava/lang/String;Ljava/lang/Integer;)Ljava/lang/Integer;")!!
      decodeRef77435034 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "decode",
          "(Ljava/lang/String;)Ljava/lang/Integer;")!!
      compareToRef1688332075 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "compareTo",
          "(Ljava/lang/Integer;)I")!!
      compareRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "compare", "(II)I")!!
      compareUnsignedRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "compareUnsigned", "(II)I")!!
      toUnsignedLongRef1263138 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toUnsignedLong",
          "(I)J")!!
      divideUnsignedRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "divideUnsigned",
          "(II)I")!!
      remainderUnsignedRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "remainderUnsigned", "(II)I")!!
      highestOneBitRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "highestOneBit",
          "(I)I")!!
      lowestOneBitRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "lowestOneBit",
          "(I)I")!!
      numberOfLeadingZerosRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "numberOfLeadingZeros", "(I)I")!!
      numberOfTrailingZerosRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "numberOfTrailingZeros", "(I)I")!!
      bitCountRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "bitCount", "(I)I")!!
      rotateLeftRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "rotateLeft",
          "(II)I")!!
      rotateRightRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "rotateRight",
          "(II)I")!!
      reverseRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "reverse", "(I)I")!!
      signumRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "signum", "(I)I")!!
      reverseBytesRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "reverseBytes",
          "(I)I")!!
      sumRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "sum", "(II)I")!!
      maxRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "max", "(II)I")!!
      minRef39187080 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "min", "(II)I")!!
      compareToRef1565685764 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "compareTo",
          "(Ljava/lang/Object;)I")!!
    }
  }
}
