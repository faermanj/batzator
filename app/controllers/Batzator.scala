package controllers

import models.Thing
import play.api.data.Form
import play.api.data.Forms.nonEmptyText
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import models.Name
import models.NameTO

object Batzator extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def newNames = Action { implicit request =>
    val json = request.body.asJson.get
    val description = (json \ "description").asOpt[String].get
    val thing = Thing.create(description)
    val names = (json \ "names").asOpt[String].get
    Name.nominate(thing.uuid, names)
    Ok("something")
  }

  def addNames(thing: String) = Action { implicit request =>
    val json = request.body.asJson.get
    val names = (json \ "names").asOpt[String].get
    Name.nominate(thing, names)
    Ok("something")
  }

  def toJSON(names: List[NameTO]) = {
    val jsNames = names map { to =>
      Json.obj(
        "uuid" -> to.uuid,
        "name" -> to.name,
        "thing_uuid" -> to.thing_uuid,
        "thing_code" -> to.thing_code,
        "thing_description" -> to.thing_description)
    }
    Json.obj("names" -> jsNames)
  }

  def allNames = Action {
    Ok(toJSON(Name.all))
  }

  def vote(code: String, vote: Int) = Action { implicit request =>
    val identity = request.headers.get("x-batzator-identity")
    if (identity.isDefined) {
      Name.vote(identity.get, code, vote)
      Ok
    } else
      BadRequest
  }

  def likeName(uuid: String) = vote(uuid, 1)
  def indifferentName(uuid: String) = vote(uuid, 0)
  def dislikeName(uuid: String) = vote(uuid, -1)

  def thing(code: String) = Action {
    Thing.byCode(code) match {
      case Some(thing) => Ok(views.html.thing(thing))
      case None => NotFound
    }
  }

  def namesOf(thingUUID: String) = Action {
    Ok(toJSON(Name.byThing(thingUUID)))
  }

}