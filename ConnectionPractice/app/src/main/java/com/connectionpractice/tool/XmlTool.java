package com.connectionpractice.tool;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by nnit on 5/9/16.
 */
public class XmlTool {
    public HashMap<String, String> parseXml(InputStream inStream) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        // 实例化一个文档构建器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 通过文档构建器工厂获取一个文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 通过文档通过文档构建器构建一个文档实例
        Document document = builder.parse(inStream);
        //获取XML文件根节点
        Element root = document.getDocumentElement();
        //获得所有子节点
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            //遍历子节点
            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                //版本号
                if ("version".equals(childElement.getNodeName())) {
                    hashMap.put("version", childElement.getFirstChild().getNodeValue());
                }
                //软件名称
                else if (("name".equals(childElement.getNodeName()))) {
                    hashMap.put("name", childElement.getFirstChild().getNodeValue());
                }
                //下载地址
                else if (("url".equals(childElement.getNodeName()))) {
                    hashMap.put("url", childElement.getFirstChild().getNodeValue());
                }







                NodeList childNodes2 = childNode.getChildNodes();
                for (int j2 = 0; j2 < childNodes2.getLength(); j2++) {
                    //遍历子节点
                    Node childNode2 = (Node) childNodes2.item(j2);
                    if (childNode2.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement2 = (Element) childNode2;
                        //版本号
                        if ("version".equals(childElement2.getNodeName())) {
                            hashMap.put("version", childElement2.getFirstChild().getNodeValue());
                        }
                        //软件名称
                        else if (("name".equals(childElement2.getNodeName()))) {
                            hashMap.put("name", childElement2.getFirstChild().getNodeValue());
                        }
                        //下载地址
                        else if (("url".equals(childElement2.getNodeName()))) {
                            hashMap.put("url", childElement2.getFirstChild().getNodeValue());
                        }
                    }
                }












            }
        }
        return hashMap;
    }
}
