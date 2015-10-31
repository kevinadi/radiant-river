package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def newpage = Action {
    Ok(views.html.index("testmessage"))
  }

  def div(msg: Option[String]) = Action {
    msg match {
      case Some(msg) => Ok(views.html.div(msg))
      case None      => Ok(views.html.div("div default message"))
    }
  }

}


object Counter {

  var counter = 0

  def incrementstate = {
    this.counter += 1
    this.counter
  }

}
