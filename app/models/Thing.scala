package models

case class Thing(uuid: String, code: String, description: String)

object Thing {

  def all(): List[Thing] = Nil

  def create(description: String) {}

  def delete(uuid: String) {}

}

