package it.unibo.ise.lab.substitutions

import it.unibo.tuprolog.core.Clause
import it.unibo.tuprolog.core.parsing.parse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ClauseRefreshing {

    @Test
    fun refreshingMember() {
        val member = Clause.parse("member(X, [H | T]) :- member(X, T)")

        val memberCopy: Clause = TODO("refresh $member")

        assertNotEquals(member, memberCopy)
        assertTrue(member.equals(memberCopy, useVarCompleteName = false))
        assertEquals(3, member.variables.distinct().count())
        assertEquals(3, memberCopy.variables.distinct().count())
    }

    @Test
    fun refreshingGrandparent() {
        val grandparent = Clause.parse("grandparent(X, Y) :- parent(X, Z), parent(Z, Y)")

        val grandparentCopy: Clause = TODO("refresh $grandparent")

        assertNotEquals(grandparent, grandparentCopy)
        assertTrue(grandparent.equals(grandparentCopy, useVarCompleteName = false))
        assertEquals(3, grandparent.variables.distinct().count())
        assertEquals(3, grandparentCopy.variables.distinct().count())
    }
}
