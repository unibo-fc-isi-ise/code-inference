package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory

class DepthFirst(knowledgeBase: Theory) : AbstractSolver(knowledgeBase) {
    override fun updateGoals(rule: Rule, goals: List<Term>): List<Term> =
        rule.bodyItems + goals.drop(1)
}
