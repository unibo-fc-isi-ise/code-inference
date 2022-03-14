package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.core.parsing.parse
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.parse

sealed class Solution(open val query: Directive) {
    data class Yes(override val query: Directive, val unifier: Substitution.Unifier) : Solution(query) {
        val solution: Directive by lazy {
            query.apply(unifier).castToDirective()
        }

        override fun toString(): String = "yes: $solution"
    }
    data class No(override val query: Directive) : Solution(query) {
        override fun toString(): String = "no"
    }
    data class Halt(override val query: Directive, val error: String) : Solution(query) {
        override fun toString(): String = "halt: $error"
    }
}

fun solve(theory: Theory, firstGoal: Struct, vararg goals: Struct): Sequence<Solution> {
    return solve(theory, Directive.of(firstGoal, *goals))
}

fun solve(theory: Theory, goals: Directive): Sequence<Solution> = sequence {
    for (solution in solve(theory, goals, goals.bodyItems.toList(), Substitution.empty())) {
        yield(solution)
        if (solution is Solution.Halt) return@sequence
    }
    yield(Solution.No(goals))
}

private fun solve(theory: Theory, query: Directive, goals: List<Term>, unifier: Substitution): Sequence<Solution> {
//    println("$goals / $unifier")
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
                    for (rule in theory[goal].map { it.freshCopy() }) {
                        val substitution = unifier + (goal mguWith rule.head)
                        yieldAll(
                            solve(theory, query, rule.bodyItems + goals.drop(1), substitution)
                        )
                    }
                }
            }
        }
    }
}

fun main() {
    val theory = Theory.parse("""
        member(X, [X | _]).
        member(X, [_ | T]) :- member(X, T).
    """.trimIndent())
    val goal = Struct.parse("member(X, [1, 2, 3])")
    for (solution in solve(theory, goal)) {
        println(solution)
    }
}