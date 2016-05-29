package com.basic.dom4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dell-pc on 2016/5/29.
 */
public class Dom4jTest {
    private Logger logger= LoggerFactory.getLogger(Dom4jTest.class);

    @Test
    public void ReadXml() throws Exception {
        String fileName="xml/books.xml";
        SAXReader reader=new SAXReader();
        Document document=reader.read(new File(fileName));
        Element bookstore=document.getRootElement();
        //通过element的elementIterator()获得迭代器
        Iterator it=bookstore.elementIterator();
        //遍历迭代器获取根节点的信息
        while (it.hasNext()){
            Element book= (Element) it.next();
            logger.info((book.attribute("id").getValue()));
            List<Attribute> attrs=book.attributes();
            for(Attribute attr:attrs){
                logger.info("节点名: "+attr.getName()+" 节点值: "+attr.getValue());
            }

            Iterator itt=book.elementIterator();
            while (itt.hasNext()){
                Element bookChild= (Element) itt.next();
                if(bookChild.getName().equals("author"))
                    logger.info(bookChild.getStringValue());
                //logger.info("节点名: "+bookChild.getName()+" 节点值: "+bookChild.getStringValue());
            }
        }
    }

    @Test
    public void WriteXML() throws Exception {
        String fileName="xml/rss.xml";
        Document document= DocumentHelper.createDocument();
        Element rss=document.addElement("rss");
        rss.addAttribute("version","2.0");
        XMLWriter xmlWriter=new XMLWriter(new FileOutputStream(fileName));
        xmlWriter.write(document);
        xmlWriter.close();
    }
}
