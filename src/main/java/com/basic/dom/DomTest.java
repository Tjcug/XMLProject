package com.basic.dom;

import org.junit.Test;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;

/**
 * Created by dell-pc on 2016/5/29.
 */
public class DomTest {

    public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        //创建一个DocumentBuilderFactory对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //创建一个DocumentBuilder对象
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        return documentBuilder;
    }

    @Test
    public void readXML() throws Exception {

        //通过DocumentBuilder的parese方法得到Document对象
        Document document = getDocumentBuilder().parse("xml/books.xml");
        NodeList nodeList = document.getElementsByTagName("book");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NamedNodeMap attrs = node.getAttributes();//获取book节点的所有属性值
            for (int j = 0; j < attrs.getLength(); j++) {
                Node node1 = attrs.item(j);
                //获取属性名字
                System.out.print("属性名: ");
                node1.getNodeName();
                //获取属性值
                System.out.println("属性值: " + node1.getNodeValue());
            }

            //下面通过Element方式获得属性值
            Element element = (Element) nodeList.item(i);
            System.out.println("属性名：" + element.getAttribute("id"));

            System.out.println("-------------------------节点的属性打印--------------------");
            NodeList childNodes = node.getChildNodes();
            for (int num = 0; num < childNodes.getLength(); num++) {
                Node temp = childNodes.item(num);
                if (temp.getNodeType() == Node.ELEMENT_NODE)
                    System.out.println("属性名：" + temp.getNodeName() + "属性值: " + temp.getFirstChild().getNodeValue());
            }
        }
    }

    @Test
    public void writeXML() throws Exception {
        String fileName="xml/books2.xml";
        DocumentBuilder documentBuilder = getDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlStandalone(true);
        Element bookStore = document.createElement("bookstore");

        Element book=document.createElement("book");
        book.setAttribute("id","01");
        Element name=document.createElement("name");
        name.setTextContent("小王子");
        Element author=document.createElement("author");
        author.setTextContent("谭杰");
        book.appendChild(author);
        book.appendChild(name);

        bookStore.appendChild(book);
        document.appendChild(bookStore);

        //创建TransformerFactory
        TransformerFactory transformerFactory= TransformerFactory.newInstance();
        Transformer transformer=transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");//实现缩进
        transformer.transform(new DOMSource(document),new StreamResult(new FileOutputStream(fileName)));
    }
}
