package controllers.ApplicationSystem

import play.api.data.Field
import play.api.i18n.MessagesProvider
import play.data.DynamicForm
import play.twirl.api.Html
import views.html.b4
import views.html.b4.vertical

import scala.xml.{Elem, Node}

object DOMParser {
  def toHtml(form: DynamicForm)(node: Node): Html = {
      Html(parser(form)(node))
  }

  private def parser(form: DynamicForm)(node: Node): String = {


    for (elem <- node) {
      val tag = elem.label
      val name = elem.attribute("name").get.head.label
      val id = elem.attribute("id").get.head.label
      elem.label match {
        case "section" =>
          "<div id=\"" + id + " class=\"section\"><h1>" + name + " </h1>" + parser(form)(elem) + "</div>"
        case "group" =>
          "<div id=\"" + id + " class=\"group\"><h1>" + name + " </h1>" + parser(form)(elem) + "</div>"
        case "extension" =>
          "<div id=\"" + id + " class=\"extension\"><h1>" + name + " </h1>" + parser(form)(elem) + "</div>"
        case "component" =>
          val comp_type = elem.attribute("type").get.head.label
          comp_type match {
            case "boolean" =>
              "<p>boolean</p>"
            case "text" =>
              "<p>text</p>"
            case "number" =>
              "<p>number</p>"
          }
        case "list" =>
          "<div id=\"" + id + " class=\"list\"><h1>" + name + " </h1>" + parser(form)(elem) + "</div>"
        case "question" =>
          "<div id=\"" + id + " class=\"question\"><h1>" + name + " </h1>" + parser(form)(elem) + "</div>"
        case "value" =>
          "<div id=\"" + id + " class=\"value\"><h1>" + name + " </h1>" + parser(form)(elem) + "</div>"
        case _ =>
          ""
      }
    }
    ""
  }

  def check(form: DynamicForm)(node: Node): String = {
    val v = toHtml(form)(node)
    val s = v.toString()
    s
  }
}

