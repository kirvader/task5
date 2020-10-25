import org.junit.jupiter.api.Assertions

internal class VisitorsKtTest {
    fun expression1(): Node {
        val var1 = Variable(2)
        val var2 = Variable(1)
        val var3 = Variable(4)
        val sum12 = Sum(var1, var2)
        return Multiply(sum12, var3)
    }

    fun expression2(): Node {
        val var1 = Variable(8)
        val mul1 = Multiply(Multiply(var1, var1), Sum(var1, var1))
        return Multiply(mul1, var1)
    }

    fun expression3(): Node {
        val var1 = Variable(123)
        val var2 = Variable(1)
        val var3 = Variable(3)
        val var4 = Variable(23)
        val var5 = Variable(12)
        val var6 = Variable(3)
        val var7 = Variable(12)
        val sum1 = Sum(var2, var3)
        val sum2 = Sum(var4, var4)
        val sum3 = Sum(var6, var6)
        val mul1 = Multiply(var1, sum1)
        val mul2 = Multiply(mul1, sum2)
        val mul3 = Multiply(sum3, var5)
        val sum4 = Sum(mul3, var6)
        val sum5 = Sum(sum4, var6)
        val mul4 = Multiply(var5, sum5)
        val mul5 = Multiply(mul4, var7)
        return Sum(mul2, mul5)
    }

    @org.junit.jupiter.api.Test
    fun printVisitorTest1() {
        Assertions.assertEquals("((2 + 1) * 4)", printVisitor(expression1()))
    }

    @org.junit.jupiter.api.Test
    fun printVisitorTest2() {
        Assertions.assertEquals("(((8 * 8) * (8 + 8)) * 8)", printVisitor(expression2()))
    }

    @org.junit.jupiter.api.Test
    fun printVisitorTest3() {
        Assertions.assertEquals("(((123 * (1 + 3)) * (23 + 23)) + ((12 * ((((3 + 3) * 12) + 3) + 3)) * 12))", printVisitor(expression3()))
    }

    @org.junit.jupiter.api.Test
    fun calculateVisitorTest1() {
        Assertions.assertEquals(12, calculateVisitor(expression1()))
    }

    @org.junit.jupiter.api.Test
    fun calculateVisitorTest2() {
        Assertions.assertEquals(8192, calculateVisitor(expression2()))
    }

    @org.junit.jupiter.api.Test
    fun calculateVisitorTest3() {
        Assertions.assertEquals(33864, calculateVisitor(expression3()))
    }

    @org.junit.jupiter.api.Test
    fun expandVisitorTest1() {
        Assertions.assertEquals("2 * 4 + 1 * 4", expandVisitor(expression1()))
    }

    @org.junit.jupiter.api.Test
    fun expandVisitorTest2() {
        Assertions.assertEquals("8 * 8 * 8 * 8 + 8 * 8 * 8 * 8", expandVisitor(expression2()))
    }

    @org.junit.jupiter.api.Test
    fun expandVisitorTest3() {
        Assertions.assertEquals("123 * 1 * 23 + 123 * 1 * 23 + 123 * 3 * 23 + 123 * 3 * 23 + 12 * 3 * 12 * 12 + 12 * 3 * 12 * 12 + 12 * 3 * 12 + 12 * 3 * 12", expandVisitor(expression3()))
    }
}