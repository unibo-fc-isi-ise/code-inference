package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith

abstract class AbstractSolver(override val knowledgeBase: Theory) : Solver {

    override fun solve(goals: Directive): Sequence<Solution> = sequence {
        for (solution in solve(goals, goals.bodyItems.toList(), Substitution.empty())) {
            yield(solution)
            if (solution is Solution.Halt) return@sequence
        }
        yield(Solution.No(goals))
    }

    private fun solve(query: Directive, goals: List<Term>, unifier: Substitution): Sequence<Solution> {
//        println("$goals / $unifier")
        return if (unifier !is Substitution.Unifier || goals.isEmpty()) {
            emptySequence()
        } else {
            sequence {
                when (val goal = goals.first().apply(unifier)) {
                    !is Struct -> {
                        yield(Solution.Halt(query, "Invalid goal $goal"))
                    }
                    is Truth -> {
                        if (goal.isTrue) {
                            yield(Solution.Yes(query, unifier))
                        }
                    }
                    else -> {
                        for (rule in knowledgeBase[goal].map { it.freshCopy() }) {
                            val substitution = unifier + (goal mguWith rule.head)
                            yieldAll(
                                solve(query, updateGoals(rule, goals), substitution)
                            )
                        }
                    }
                }
            }
        }
    }

    protected abstract fun updateGoals(rule: Rule, goals: List<Term>): List<Term>
}
