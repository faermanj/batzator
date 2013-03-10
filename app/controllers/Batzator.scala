package controllers

import models.Thing

import play.api.data.Form
import play.api.data.Forms.nonEmptyText 
import play.api._
import play.api.mvc._


object Batzator extends Controller {
  val thingForm = Form(
    "description" -> nonEmptyText)

  def index = Action { 
    Ok(views.html.index(Thing.all,thingForm))
  }
  
  def newThing = TODO 

}