package jp.mofbrown.resource.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XPathを簡易的に利用するクラス
 * @author mkudo
 *
 */
public class SimpleXPath {

	public SimpleXPath(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		document = builder.parse(new File(xml));
		xpath = XPathFactory.newInstance().newXPath();
		expressionCache = new HashMap<String, XPathExpression>();
	}
	
	/**
	 * Nodeを取得します
	 * @param expression
	 * @return
	 */
	public Node getNode(String expression) {
		XPathExpression xpathExpression = getExpressionCache(expression);
		
		try {
			if (xpathExpression != null) {
				return (Node) xpathExpression.evaluate(document, XPathConstants.NODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * NodeListを取得します
	 * @param expression
	 * @return
	 */
	public NodeList getNodeList(String expression) {
		XPathExpression xpathExpression = getExpressionCache(expression);
		
		try {
			if (xpathExpression != null) {
				return (NodeList) xpathExpression.evaluate(document, XPathConstants.NODESET);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Nodeのボディ部を取得します
	 * @param expression
	 * @return
	 */
	public String getNodeBody(String expression) {
		Node node = getNode(expression);
		
		if (node != null) {
			return node.getFirstChild().getNodeValue();
		}
		return null;
	}
	
	/**
	 * NodeListのボディ部をListで取得します
	 * @param expression
	 * @return
	 */
	public List<String> getNodeListBody(String expression) {
		NodeList nodeList = getNodeList(expression);
		
		List<String> bodyList = new ArrayList<String>();
		
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Node firstChild = node.getFirstChild();
				
				if (firstChild != null) {
					bodyList.add(firstChild.getNodeValue());
				}
			}
		}
		
		return bodyList;
	}
	
	/**
	 * XPathExpressionを取得します。
	 * 既にコンパイル済みの場合はキャッシュから取得します
	 * @param expression
	 * @return
	 */
	private XPathExpression getExpressionCache(String expression) {
		try {
			XPathExpression xpathExpression = expressionCache.get(expression);
			
			if (xpathExpression == null) {
				xpathExpression = xpath.compile(expression);
			}

			return xpathExpression;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Document
	 */
	private final Document document;
	
	/**
	 * XPath
	 */
	private final XPath xpath;
	
	/**
	 * XPathExpressionのキャッシュ用Map
	 */
	private final Map<String, XPathExpression> expressionCache;
	
}
