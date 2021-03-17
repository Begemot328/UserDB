package org.example.users.dom.impl;

import org.example.users.dom.UserDOM;
import org.example.users.entity.Role;
import org.example.users.entity.User;
import org.example.users.entity.UserFactory;
import org.example.users.exceptions.UserDOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class UserDOMImpl implements UserDOM {

    private static final String USER = "user";
    private static final String FIRSTNAME = "first-name";
    private static final String LASTNAME = "last-name";
    private static final String MAIL = "mail";
    private static final String ROLES = "roles";
    private static final String PHONES = "phones";
    private static final String ROLE = "role";
    private static final String PHONE = "phone";
    private static final String EXTENSION = ".xml";
    private static final String PATH = "users/";

    private final Document document;
    private final DocumentBuilder documentBuilder;

    public UserDOMImpl() throws UserDOMException {
        try {
            DocumentBuilderFactory documentBuilderFactory
                    = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            Path path = Paths.get(PATH);
            Files.createDirectories(path);
        } catch (ParserConfigurationException | IOException e) {
            throw new UserDOMException(e);
        }

    }

    public void save(User user) throws UserDOMException {
        document.appendChild(getUserElement(user));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            File file = new File(PATH + user.getId() + EXTENSION);
            StreamResult result = new StreamResult(
                    new FileWriter(PATH + user.getId() + EXTENSION));
            transformer.transform(source, result);
        } catch (TransformerException | IOException e) {
            throw new UserDOMException(e);
        }
    }

    public void delete(User user) throws UserDOMException {
        try {
            Files.delete(new File(PATH + user.getId() + EXTENSION).toPath());
        } catch (IOException e) {
            throw new UserDOMException("User not found");
        }
    }

    public User open(int id) throws UserDOMException {
        Document doc = null;
        User result;
        try {
            doc = documentBuilder.parse(PATH + id + EXTENSION);
        } catch (SAXException | IOException e) {
            throw new UserDOMException(e);
        }
        doc.normalizeDocument();
        Element root = doc.getDocumentElement();

        result = parseUser(root);
        result.setId(id);
        return result;
    }

    public Set<User> findAll() throws UserDOMException {
        Set<User> result = new HashSet<>();
        File[] folder = new File(PATH).listFiles();
        if (folder == null || folder.length == 0) {
            return result;
        }

        for (File file : folder) {
            try {
                result.add(
                        open(Integer.parseInt(file.getName().replace(EXTENSION, ""))));
            } catch (NumberFormatException e) {
                break;
            }
        }
        return result;
    }

    public void saveAll(Set<User> set) throws UserDOMException {
        for (User user : set) {
            save(user);
        }
    }

    private User parseUser(Element userElement) throws UserDOMException {
        Element element;
        Node node;
        Set<String> phones;
        Set<Role> roles;

        roles = parseRoles(getElement(userElement, ROLES));
        phones = parsePhones(getElement(userElement, PHONES));
        return new UserFactory().createUser(
                getElementTextContent(userElement, FIRSTNAME),
                getElementTextContent(userElement, LASTNAME),
                getElementTextContent(userElement, MAIL),
                phones, roles);
    }

    private Set<Role> parseRoles(Element rolesElement) {
        Set<Role> roles = new HashSet<>();
        NodeList nList = rolesElement.getElementsByTagName(ROLE);
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            String role = node.getTextContent();
            roles.add(Role.valueOf(role));
        }
        return roles;
    }

    private Set<String> parsePhones(Element phonesElement) {
        Set<String> phones = new HashSet<>();
        NodeList nList = phonesElement.getElementsByTagName(PHONE);
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            phones.add(node.getTextContent());
        }
        return phones;
    }

    private Element getElement(Element element, String name) throws UserDOMException {
        Node node = element.getElementsByTagName(name).item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            element = (Element) node;
            return element;
        }
        throw new UserDOMException("Node not found");

    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }

    private Element getUserElement(User user) {
        Element rootElement = document.createElement(USER);

        rootElement.appendChild(textElement(FIRSTNAME, user.getFirstName()));
        rootElement.appendChild(textElement(LASTNAME, user.getLastName()));
        rootElement.appendChild(textElement(MAIL, user.getMail()));

        Element roleElement = document.createElement(ROLES);
        for (Role role : user.getRoles()) {
            roleElement.appendChild(textElement(ROLE, role.name()));
        }
        rootElement.appendChild(roleElement);

        Element phoneElement = document.createElement(PHONES);
        for (String phone : user.getPhoneNumbers()) {
            phoneElement.appendChild(textElement(PHONE, phone));
        }
        rootElement.appendChild(phoneElement);

        return rootElement;
    }

    private Element textElement(String name, String value) {
        Element element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }

}
