package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith

class SmartSolver(knowledgeBase: Theory) : AbstractSolver(knowledgeBase) {
    override suspend fun SequenceScope<Solution>.handleRules(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct,
        matchingRules: Sequence<Rule>
    ) {
        TODO("order the matching rules accordingly, then call handleRulesInARow")
    }

    /**
     * A method checking whether a [Rule] is recursive or not (in the sense that it calls itself in its own body)
     */
    private fun isRecursive(rule: Rule): Boolean = when {
        rule.bodyItems.any { it is Struct &&  it.indicator == rule.head.indicator } -> true
        else -> false
    }

    private val nonRecursiveFirst = Comparator<Rule> { o1, o2 ->
        when {
            isRecursive(o1) == isRecursive(o2) -> 0
            !isRecursive(o1) && isRecursive(o2) -> -1
            else -> 1
        }
    }
}
