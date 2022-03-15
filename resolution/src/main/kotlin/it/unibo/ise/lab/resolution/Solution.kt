package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.core.*

sealed class Solution(open val query: Directive) {
    data class Yes(override val query: Directive, val unifier: Substitution.Unifier) : Solution(query) {
        val solvedQuery: Directive by lazy {
            query.apply(unifier).castToDirective()
        }

        override fun toString(): String = "yes: $solvedQuery"
    }

    data class No(override val query: Directive) : Solution(query) {
        override fun toString(): String = "no"
    }

    data class Halt(override val query: Directive, val error: String) : Solution(query) {
        override fun toString(): String = "halt: $error"
    }
}