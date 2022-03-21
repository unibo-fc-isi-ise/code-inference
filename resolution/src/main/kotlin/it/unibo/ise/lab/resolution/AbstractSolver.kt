package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory

abstract class AbstractSolver(override val knowledgeBase: Theory) : Solver {

    override fun solve(goals: Directive): Sequence<Solution> {
        val streamOfSolutions = solve(goals, goals.bodyItems.toList(), Substitution.empty())
        return sequence {
            for (solution in streamOfSolutions) {
                yield(solution)
                // computes no more solutions after an error has occurred
                if (solution is Solution.Halt) return@sequence
            }
            // add one final negative solution to signal that no more solutions are available
            yield(Solution.No(goals))
        }
    }

    /**
     * Handles the intermediate resolution step.
     * Lazily computes non-negative [Solution]s for the [remainingGoals], provided that
     * - the solver is currently handling a [query] provided by the user
     * - some goals have already been proved, hence constructing the partial resolving [unifier]
     * - some other goals are yet to be proved, namely [remainingGoals]
     */
    protected fun solve(query: Directive, remainingGoals: List<Term>, unifier: Substitution): Sequence<Solution> {
        println("$remainingGoals / $unifier")
        return if (unifier !is Substitution.Unifier || remainingGoals.isEmpty()) {
            emptySequence()
        } else {
            sequence {
                when (val currentGoal = remainingGoals.first().apply(unifier)) {
                    !is Struct -> {
                        yield(Solution.Halt(query, "Invalid goal $currentGoal"))
                    }
                    is Truth -> {
                        if (currentGoal.isTrue) {
                            yield(Solution.Yes(query, unifier))
                        }
                    }
                    else -> {
                        val matchingRules = knowledgeBase[currentGoal].map { it.freshCopy() }
                        handleRules(query, remainingGoals.drop(1), unifier, currentGoal, matchingRules)
                    }
                }
            }
        }
    }

    /**
     * Handles the many [matchingRules] for the [currentGoal], as well as the many [remainingGoals], necessary to prove [query]
     */
    protected abstract suspend fun SequenceScope<Solution>.handleRules(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution,
        currentGoal: Struct,
        matchingRules: Sequence<Rule>
    )
}
