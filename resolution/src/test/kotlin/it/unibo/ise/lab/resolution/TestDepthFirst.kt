package it.unibo.ise.lab.resolution

import it.unibo.ise.lab.resolution.Theories.member
import it.unibo.tuprolog.core.Directive
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.parsing.parse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestDepthFirst {

    @Test
    fun testMemberX123() {
        val goal = Struct.parse("member(X, [1, 2, 3])")
        println("?:- $goal.")
        val solver = DepthFirst(member)
        val solutions = solver.solve(goal).take(4).map{ println(it); it }.toList()

        assertEquals(4, solutions.size)

        for (i in 0 .. 2) {
            solutions[i].let {
                assertIs<Solution.Yes>(it)
                assertEquals(Directive.of(goal), it.query)
                assertEquals(Struct.parse(":- member(${i + 1}, [1, 2, 3])"), it.solvedQuery)
            }
        }

        solutions[3].let {
            assertIs<Solution.No>(it)
            assertEquals(Directive.of(goal), it.query)
        }
    }

    @Test
    fun testMemberXY() {
        val goal = Struct.parse("member(X, Y)")
        println("?:- $goal.")
        val solver = DepthFirst(member)

        solver.solve(goal).take(10).map{ println(it); it }.forEachIndexed{ i, solution ->
            assertIs<Solution.Yes>(solution)
            assertEquals(i + 3, solution.solvedQuery.variables.count())
        }
    }


}