package com.cooper.poc.repo.words

import java.util.UUID

case class Word(id: UUID, word: String, idx: Int, isRef: Boolean)
