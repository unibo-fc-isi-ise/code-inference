package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory

abstract class AbstractSolver(override val knowledgeBase: Theory) : Solver {

    override fun solve(goals: Directive): Sequence<Solution> = sequence {
        for (solution in solve(goals, goals.bodyItems.toList(), Substitution.empty())) {
            yield(solution)
            if (solution is Solution.Halt) return@sequence
        }
        yield(Solution.No(goals))
    }

    protected fun solve(query: Directive, goals: List<Term>, unifier: Substitution): Sequence<Solution> {
        println("$goals / $unifier")
        return if (unifier !is Substitution.Unifier || goals.isEmpty()) {
            emptySequence()
        } else {
            sequence {
                when (val currentGoal = goals.first().apply(unifier)) {
                    !is Struct -> {
                        yield(Solution.Halt(query, "Invalid goal $currentGoal"))
                    }
                    is Truth -> {
                        if (currentGoal.isTrue) {
                            yield(Solution.Yes(query, unifier))
                        }
                    }
                    else -> {
                        handleRules(
                            query, goals.drop(1), unifier, currentGoal, knowledgeBase[currentGoal].map { it.freshCopy() }
                        )
                    }
                }
            }
        }
    }

    protected abstract suspend fun SequenceScope<Solution>.handleRules(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution,
        currentGoal: Struct,
        rules: Sequence<Rule>
    )
}
