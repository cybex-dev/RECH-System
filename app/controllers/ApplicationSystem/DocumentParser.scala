package controllers.ApplicationSystem

import javax.xml.transform.dom.DOMSource
import javax.xml.transform.sax.SAXResult

import scala.xml.Node
import scala.xml.parsing.NoBindingFactoryAdapter

object DocumentParser {
  def asXmlNode(dom: org.w3c.dom.Document): Node = {
    val source = new DOMSource(dom)
    val v  = source.getNode
    val adapter = new NoBindingFactoryAdapter
    val saxResult = new SAXResult(adapter)
    val transformerFactory = javax.xml.transform.TransformerFactory.newInstance()
    val transformer = transformerFactory.newTransformer()
    transformer.transform(source, saxResult)
    adapter.rootElem
  }
}
