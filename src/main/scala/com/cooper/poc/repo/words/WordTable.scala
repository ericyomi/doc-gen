package com.cooper.poc.repo.words

import java.util.UUID

import slick.lifted.{TableQuery, Tag}
import com.cooper.poc.repo.DocGenMySQLProfile.api._

trait WordTable {
  protected val words = TableQuery[WordRow]

  class WordRow(tag: Tag) extends Table[Word](tag, "words") {
    def * = (id, word, index, isRef) <> ((Word.apply _).tupled, Word.unapply)
    def id = column[UUID]("id", O.PrimaryKey)
    def word = column[String]("word")
    def isRef = column[Boolean]("is_ref")
    def index = column[Int]("idx")
  }
}
