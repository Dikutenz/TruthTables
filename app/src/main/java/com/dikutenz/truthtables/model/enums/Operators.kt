package com.dikutenz.truthtables.model.enums

import com.dikutenz.truthtables.model.LogicOperations.andChar
import com.dikutenz.truthtables.model.LogicOperations.eqChar
import com.dikutenz.truthtables.model.LogicOperations.implyChar
import com.dikutenz.truthtables.model.LogicOperations.nandChar
import com.dikutenz.truthtables.model.LogicOperations.norChar
import com.dikutenz.truthtables.model.LogicOperations.notChar
import com.dikutenz.truthtables.model.LogicOperations.orChar
import com.dikutenz.truthtables.model.LogicOperations.xorChar

val setOperators: Set<Char> = setOf(
    andChar, orChar, implyChar, eqChar, notChar, xorChar, nandChar, norChar
)