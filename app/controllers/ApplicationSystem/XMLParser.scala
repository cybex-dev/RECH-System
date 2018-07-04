package controllers.ApplicationSystem

import com.sun.org.apache.xalan.internal.xsltc.trax.DOM2SAX
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.sax.SAXResult

import scala.xml.Node
import scala.xml.parsing.NoBindingFactoryAdapter

object XMLParser {
  def asXml(dom: org.w3c.dom.Node): Node = {
    val dom2sax = new DOM2SAX(dom)
    val adapter = new NoBindingFactoryAdapter
    dom2sax.setContentHandler(adapter)
    dom2sax.parse()
    adapter.rootElem
  }

  def parser(root: Node): List[Node] = {
    //TODO
  }
}
