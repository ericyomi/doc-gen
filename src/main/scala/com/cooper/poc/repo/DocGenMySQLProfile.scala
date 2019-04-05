package com.cooper.poc.repo

import java.sql.{PreparedStatement, ResultSet}

import slick.ast.FieldSymbol
import slick.jdbc.MySQLProfile

trait DocGenMySQLProfile extends MySQLProfile {
  override val columnTypes = new JdbcTypes

  trait ImplicitColumnTypes extends super.ImplicitColumnTypes {
    implicit override def uuidColumnType: columnTypes.UUIDJdbcType = columnTypes.uuidJdbcType
  }

  class JdbcTypes extends super.JdbcTypes {
    override val uuidJdbcType = new UUIDJdbcType {
      import java.util.UUID

      override def sqlType: Int = java.sql.Types.CHAR

      override def sqlTypeName(sym: Option[FieldSymbol]): String = "CHAR(36)"

      override def getValue(r: ResultSet, idx: Int): UUID = {
        val uuidFromDb: String = r.getString(idx)
        if(uuidFromDb == null) null
        else UUID.fromString(uuidFromDb)
      }

      override def setValue(v: UUID, p: PreparedStatement, idx: Int): Unit = p.setString(idx, v.toString)

      override def updateValue(v: UUID, r: ResultSet, idx: Int): Unit = r.updateString(idx, v.toString)
    }
  }
}

object DocGenMySQLProfile extends DocGenMySQLProfile