package it.unibo.ise.lab.resolution

import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.parse

object Theories {
    val member = Theory.parse("""
        member(X, [X | _]).
        member(X, [_ | T]) :- member(X, T).
    """.trimIndent())

    val inverseMember = Theory.parse("""
        member(X, [_ | T]) :- member(X, T).
        member(X, [X | _]).
    """.trimIndent())
}