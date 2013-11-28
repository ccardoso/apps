/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maisis.healthy.pps2.mavenproject1;

import healthy.Archetype;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * classe para leitura do campo body e escrita no campo data do arquétipo DEMO
 *
 * @author Carlos Cardoso <carlos.cardoso@maisis.pt>
 */
public class XMLfields {

    private Document xmlData;

    /**
     * *
     *
     * @param archetypeResponse
     * @return Document
     */
    public Document setXMLfields(Archetype archetypeResponse) {
        try {
            //escrever no campo data DEMO
            xmlData = DocumentHelper.parseText(archetypeResponse.getData());
            //texto livre at0005
            Node freeText = xmlData.selectSingleNode("//ns:at0005/ns:value");
            freeText.setText("qualquer descrição a respeito de: ..."); //escrever no campo value
            //quantidade at0012
            Node quantity = xmlData.selectSingleNode("//ns:at0012/ns:magnitude");
            quantity.setText("60"); //escrever no campo magnitude
            Node quatityUnits = xmlData.selectSingleNode("//ns:at0012/ns:units");
            quatityUnits.setText("mm"); //escrever no campo units
            //intervalo de quantidade at0023
            Node quantityIntervalLower = xmlData.selectSingleNode("//ns:at0023/ns:lower/ns:magnitude"); //escrever no campo magnitude (lower)
            quantityIntervalLower.setText("10");
            Node quantityIntervalLowerUnits = xmlData.selectSingleNode("//ns:at0023/ns:lower/ns:units"); //escrever no campo units (lower)
            quantityIntervalLowerUnits.setText("cm");
            Node quantityIntervalUpper = xmlData.selectSingleNode("//ns:at0023/ns:upper/ns:magnitude");
            quantityIntervalUpper.setText("100");
            Node quantityIntervalUpperUnits = xmlData.selectSingleNode("//ns:at0023/ns:upper/ns:units");
            quantityIntervalUpperUnits.setText("cm");
            //contagem at0013
            Node count = xmlData.selectSingleNode("//ns:at0013/ns:magnitude");
            count.setText("3");
            //intervalo de um numero inteiro at0022
            Node intIntervalLower = xmlData.selectSingleNode("//ns:at0022/ns:lower/ns:magnitude");
            intIntervalLower.setText("5");
            Node intIntervalUpper = xmlData.selectSingleNode("//ns:at0022/ns:upper/ns:magnitude");
            intIntervalUpper.setText("20");
            //data at0014
            Node data = xmlData.selectSingleNode("//ns:at0014/ns:value");
            data.setText("2013-11-26");
            //Ordinal at0015
            //para este exemplo vamos ler o campo body para se ir buscar os valores a persistir
            //ler o body
            Document xmlBody = DocumentHelper.parseText(archetypeResponse.getBody());
            List<Element> nodes = xmlBody.selectNodes("//ns:attribute[@id='at0015']/list/value");
            List<String> fields = new ArrayList<>(0);
            for (Element node : nodes) {
                String nodeData = node.getStringValue();
                fields.add(nodeData.toString());
                System.out.println("lista: " + nodeData);
            }
            //escrever no campo data um valor que venha do campo body
            Node ordinal = xmlData.selectSingleNode("//ns:at0015/ns:value");
            ordinal.setText(fields.get(5)); //0(No pain) a 5(Most severe pain imaginable)
            //String ordinalValue = ordinal.getText();
            //pesquisar no campo body a descrição para o ordinal 5
            Node ordinalValueText = xmlBody.selectSingleNode("//ns:description/ns:language[@id='en']/ns:attr[@id='at0043']/@name");
            Node ordinalText = xmlData.selectSingleNode("//ns:at0015/ns:symbol/ns:definingCode/ns:codeString");
            ordinalText.setText(ordinalValueText.getText());           
            //Boleano at0016
            Node bool = xmlData.selectSingleNode("//ns:at0016/ns:definingCode/ns:codeString");
            bool.setText("true");
            //dados multimédia at0026
            Node mult = xmlData.selectSingleNode("//ns:at0026/ns:size");
            mult.setText("200kb");
            //URI at0027
            Node uri = xmlData.selectSingleNode("//ns:at0027");
            uri.setText("healthy.oobian.com");
            System.out.println("dataModified: " + xmlData.asXML());
            return xmlData;
        } catch (DocumentException ex) {
            Logger.getLogger(XMLfields.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Document getXmlData() {
        return xmlData;
    }
}
