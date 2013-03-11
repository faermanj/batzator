package models

import java.util.UUID

case class Thing(uuid: String, code: String, description: String)

object Thing extends Model{

  import anorm._
  import anorm.SqlParser._

  import play.api.db._
  import play.api.Play.current

  val thing = {
    get[String]("uuid") ~
      get[String]("code") ~
      get[String]("description") map {
        case uuid ~ code ~ description => Thing(uuid, code, description)
      }
  }
 
  
  def all(): List[Thing] = DB.withConnection { implicit c =>
    SQL("select * from Thing").as(thing *)
  }

  def byCode(code:String): Option[Thing] = DB.withConnection { implicit c =>
    SQL("select * from Thing where code={code}")
    	.on('code -> code)
    	.as(thing.singleOpt)    	
  }

  def create(description: String) = {
    val uuid = randomUUID
    val code = uuid
    DB.withConnection { implicit c =>
      SQL("insert into Thing (uuid,code,description) values ({uuid},{code},{description})").on(
        'uuid -> uuid,
        'code -> code,
        'description -> description).executeUpdate()
    }
    Thing(uuid, code, description)
  }




  def delete(uuid: String) {
    DB.withConnection { implicit c =>
      SQL("delete from Thing where uuid = {uuid}").on(
        'uuid -> uuid).executeUpdate()
    }
  }
  
 

}

