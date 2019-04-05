package com.cooper.poc.repo.words

import java.util.UUID

import slick.dbio.Effect.Read
import slick.dbio.{DBIOAction, NoStream}
import com.cooper.poc.repo.DocGenMySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global

class WordsRepo extends WordTable {
  def find(indices: Seq[Int]): DBIOAction[Seq[Word], NoStream, Read] = words.filter(_.index inSet indices).result

  def findById(id: UUID): DBIOAction[Word, NoStream, Read] = words.filter(_.id === id).result.map {
    case word +:_ => word
    case _ => throw new RuntimeException(s"Word[$id] not found!")
  }
}
