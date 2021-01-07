package readwrite;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLFileHandler implements MyFileHandler, MyConstants, FilePath{
    public void read(){
        try {
            File xmlFile = new File(INPUTPATH +".xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("employee");

            MyCollection myCollection = MyCollection.getInstance();

            //todo : change the logic to read till the end of the file
            for(int itr=0;itr<WRITE_MAX_VALUE;itr++){
                Node node = nodeList.item(itr);
                Employee emp=new Employee();

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element e=(Element) node;

                    emp.setFirstName(e.getElementsByTagName("firstName").item(0).getTextContent());
                    emp.setLastName(e.getElementsByTagName("lastName").item(0).getTextContent());
                    emp.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(e.getElementsByTagName("dateOfBirth").item(0).getTextContent()));
                    emp.setExperience(Double.parseDouble(e.getElementsByTagName("experience").item(0).getTextContent()));

                    myCollection.add(emp);
                    myCollection.incrementRead();
                }

            }
        }
        catch (Exception e){
            System.out.println(e); // E
        }
    }
    public void write(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // root element
            Element rootElement = doc.createElement("employees");
            doc.appendChild(rootElement);

            //todo : change teh collection name .. x1 does not mean anything
            MyCollection x1= MyCollection.getInstance();
            Employee emp;

            //todo : define 100 as a constant
            for(int writeItr=0;writeItr<WRITE_MAX_VALUE;writeItr++){
                emp = x1.get();

                //todo : need to look at the logic .. values are getting appended
                Element employeeElement= doc.createElement("employee");
                rootElement.appendChild(employeeElement);
                Element firstName=doc.createElement("firstName");
                firstName.appendChild(doc.createTextNode(emp.getFirstName()));
                employeeElement.appendChild(firstName);
                Element lastName=doc.createElement("lastName");
                firstName.appendChild(doc.createTextNode(emp.getLastName()));
                employeeElement.appendChild(lastName);
                Element dateOfBirth=doc.createElement("dateOfBirth");
                dateOfBirth.appendChild(doc.createTextNode(emp.getDateOfBirth()+""));
                employeeElement.appendChild(dateOfBirth);
                Element experience=doc.createElement("experience");
                firstName.appendChild(doc.createTextNode(emp.getExperience()+""));
                employeeElement.appendChild(experience);

                x1.incrementWrite();
            }

            //todo : look at moving the logic of writing content record by record
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(OUTPUTPATH +".xml"));
            transformer.transform(domSource, streamResult);

        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

}