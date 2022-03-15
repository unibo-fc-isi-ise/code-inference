package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.Directive
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.theory.Theory

interface Solver {
    val knowledgeBase: Theory

    fun solve(firstGoal: Struct, vararg goals: Struct): Sequence<Solution> =
        solve(Directive.of(firstGoal, *goals))

    fun solve(goals: Directive): Sequence<Solution>
}
