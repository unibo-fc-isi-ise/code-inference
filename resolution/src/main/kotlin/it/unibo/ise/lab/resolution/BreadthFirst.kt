package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory

class BreadthFirst(knowledgeBase: Theory) : AbstractSolver(knowledgeBase) {
    override fun updateGoals(rule: Rule, goals: List<Term>): List<Term> =
        goals.drop(1) + rule.bodyItems
}
