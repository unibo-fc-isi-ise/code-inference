package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith

class DepthFirstSolver(knowledgeBase: Theory) : AbstractSolver(knowledgeBase) {
    override suspend fun SequenceScope<Solution>.handleRules(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution,
        currentGoal: Struct,
        rules: Sequence<Rule>
    ) {
        for (rule in rules) {
            val substitution = unifier + (currentGoal mguWith rule.head)
            yieldAll(solve(query, rule.bodyItems.toList() + remainingGoals, substitution))
        }
    }
}
