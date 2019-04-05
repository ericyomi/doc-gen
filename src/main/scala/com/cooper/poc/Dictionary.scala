package com.cooper.poc

import java.util.UUID

case class Dictionary(dbIndices: Seq[Int])
case class DocumentType(documentTypeVector: Seq[Int], dictionary: Dictionary, wordIndices: Seq[Int])
case class Document(documentType: DocumentType, wordIndices: Seq[Int], id: UUID = UUID.randomUUID())
