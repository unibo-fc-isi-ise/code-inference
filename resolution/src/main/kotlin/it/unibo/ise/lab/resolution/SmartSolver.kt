package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith

class SmartSolver(knowledgeBase: Theory) : AbstractSolver(knowledgeBase) {
    override suspend fun SequenceScope<Solution>.handleRules(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution,
        currentGoal: Struct,
        rules: Sequence<Rule>
    ) {
        val sortedRules = rules.sortedWith(nonRecursiveFirst).toList()
        for (rule in sortedRules) {
            val substitution = unifier + (currentGoal mguWith rule.head)
            yieldAll(solve(query, rule.bodyItems.toList() + remainingGoals, substitution))
        }
    }

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
