
interface Node {
    fun apply(printVisitor: PrintVisitor): String
    fun apply(calculateVisitor: CalculateVisitor): Int
    fun apply(expandVisitor: ExpandVisitor): List<String>
}

class PrintVisitor {
    fun apply(node: Variable) = "${node.value}"
    fun apply(node: Multiply) = "(${node.left.apply(PrintVisitor())} * ${node.right.apply(PrintVisitor())})"
    fun apply(node: Sum) = "(${node.left.apply(this)} + ${node.right.apply(this)})"
}
class CalculateVisitor {
    fun apply(node: Variable) = node.value
    fun apply(node: Multiply) = node.left.apply(CalculateVisitor()) * node.right.apply(CalculateVisitor())
    fun apply(node: Sum) = node.left.apply(CalculateVisitor()) + node.right.apply(CalculateVisitor())
}

class ExpandVisitor {
    fun apply(node: Variable) = listOf(node.value.toString())
    fun apply(node: Multiply): List<String> {
        val listForFirst = node.left.apply(ExpandVisitor())
        val listForSecond = node.right.apply(ExpandVisitor())
        return listForFirst.fold(listOf(), { curList, curString -> curList + listForSecond.map {"$curString * $it"} } )
    }
    fun apply(node: Sum) = node.left.apply(ExpandVisitor()) + node.right.apply(ExpandVisitor())
}


class Variable(val value: Int) : Node {
    override fun apply(printVisitor: PrintVisitor) = printVisitor.apply(this)
    override fun apply(calculateVisitor: CalculateVisitor) = calculateVisitor.apply(this)
    override fun apply(expandVisitor: ExpandVisitor) = expandVisitor.apply(this)
}
class Multiply(val left: Node, val right: Node) : Node {
    override fun apply(printVisitor: PrintVisitor) = printVisitor.apply(this)
    override fun apply(calculateVisitor: CalculateVisitor) = calculateVisitor.apply(this)
    override fun apply(expandVisitor: ExpandVisitor) = expandVisitor.apply(this)
}
class Sum(val left: Node, val right: Node) : Node {
    override fun apply(printVisitor: PrintVisitor) = printVisitor.apply(this)
    override fun apply(calculateVisitor: CalculateVisitor) = calculateVisitor.apply(this)
    override fun apply(expandVisitor: ExpandVisitor) = expandVisitor.apply(this)
}
fun printVisitor(expression : Node) = expression.apply(PrintVisitor())
fun calculateVisitor(expression: Node) = expression.apply(CalculateVisitor())
fun expandVisitor(expression: Node) = expression.apply(ExpandVisitor()).joinToString(" + ")

fun expression1(): Node {
    val var1 = Variable(1)
    val var2 = Variable(2)
    val var3 = Variable(3)
    val sum12 = Sum(var1, var2)
    return Multiply(sum12, var3)
}

fun main() {
    println(printVisitor(expression1()))
    println(calculateVisitor(expression1()))
    println(expandVisitor(expression1()))
}