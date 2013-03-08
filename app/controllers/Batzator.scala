package controllers

import play.api._
import play.api.mvc._
import models.Thing

object Batzator extends Controller {
  
  def index = Action {
	  Ok(views.html.index(Thing.all()))  
  }
  
}