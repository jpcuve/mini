package com.messio.mini.session;

import com.messio.mini.entity.*;
import com.messio.mini.util.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jpc on 01/04/14.
 */
@Startup
@Singleton(name = "mini/initial-data")
public class InitialData extends DAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitialData.class);
    @Inject
    private AssetManager assetManager;

    @PostConstruct
    public void init() throws IOException, ParserConfigurationException, SAXException {
        User system = result(builder(User.class).named(User.USER_BY_USERNAME).param("username", User.SYSTEM_NAME));
        if (system == null){
            system = create(new User(User.SYSTEM_NAME, Locale.ENGLISH));
            final Preference systemRoot = create(new Preference(null, system.getId(), ""));
            final Preference folders = create(new Preference(systemRoot, system.getId(), "folders"));
            final Map<String, String> folderProperties = new HashMap<>();
            folderProperties.put("base", System.getProperty("java.io.tmpdir"));
            folderProperties.put("incoming", ".");
            folderProperties.put("invoices", ".");
            folderProperties.put("uploaded", ".");
            folders.setProperties(folderProperties);
            update(folders);

            // build test data
            final File fResource = new File(getClass().getClassLoader().getResource("../..").getFile());
            LOGGER.info("Resource folder: {}", fResource.getAbsolutePath());
            final File fAsset = new File(fResource, "assets");
            final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            final SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(getClass().getClassLoader().getResourceAsStream("init.xml"), new DefaultHandler(){
                private User currentUser = null;
                private Set<Role> roles = new HashSet<>();
                private Map<String, Court> courtMap = new HashMap<>();
                private Map<String, Pol> polMap = new HashMap<>();
                private Map<String, String> imageMap = new HashMap<>();
                private Map<String, String> pdfMap = new HashMap<>();
                private Map<String, Member> memberMap = new HashMap<>();
                private Map<String, Right> rightMap = new HashMap<>();
                private Binder currentBinder = null;
                private Docket currentDocket = null;
                private Decision currentDecision = null;

                private void updateImageIds(Right right, String[] refs){
                    final List<String> uuids = new ArrayList<String>();
                    for (String ref: refs){
                        uuids.add(imageMap.get(ref));
                    }
                    right.setImageIds(uuids);
                    update(right);
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    switch(qName){
                        case "user":{
                            currentUser = create(new User(attributes.getValue("username"), Locale.forLanguageTag(attributes.getValue("locale"))));
                            currentUser.setPassword(attributes.getValue("password"));
                            update(currentUser);
                            roles.clear();
                            break;
                        }
                        case "role":{
                            roles.add(Role.valueOf(attributes.getValue("name")));
                            break;
                        }
                        case "court":{
                            Court parent = null;
                            for (final String courtName: new Path<>(attributes.getValue("path").split(",")).getPath()){
                                Court court = result(builder(Court.class).named(Court.COURT_BY_PARENT_BY_NAME).params("parent", parent, "name", courtName));
                                if (court == null){
                                    court = create(new Court(parent, courtName));
                                }
                                parent = court;
                            }
                            courtMap.put(attributes.getValue("ref"), parent);
                            break;
                        }
                        case "pol":{
                            Pol parent = null;
                            for (final String polName: new Path<>(attributes.getValue("path").split(",")).getPath()){
                                Pol pol = result(builder(Pol.class).named(Pol.POL_BY_PARENT_BY_NAME).params("parent", parent, "name", polName));
                                if (pol == null){
                                    pol = create(new Pol(pol, polName));
                                }
                                parent = pol;
                            }
                            polMap.put(attributes.getValue("ref"), parent);
                            break;
                        }
                        case "image":{
                            try{
                                imageMap.put(attributes.getValue("ref"), assetManager.saveData(attributes.getValue("mime"), new File(fAsset, attributes.getValue("asset"))));
                            } catch (IOException x){
                                throw new SAXException(x);
                            }
                            break;
                        }
                        case "pdf":{
                            try{
                                pdfMap.put(attributes.getValue("ref"), assetManager.saveData("application/pdf", new File(fAsset, attributes.getValue("asset"))));
                            } catch (IOException x){
                                throw new SAXException(x);
                            }
                            break;
                        }
                        case "member":{
                            memberMap.put(attributes.getValue("ref"), create(new Member(attributes.getValue("name"))));
                            break;
                        }
                        case "binder":{
                            currentBinder = create(new Binder(attributes.getValue("reference"), FirstAction.valueOf(attributes.getValue("firstAction"))));
                            break;
                        }
                        case "party":{
                            if (currentBinder != null){
                                create(new Party(currentBinder, memberMap.get(attributes.getValue("member")), Boolean.parseBoolean(attributes.getValue("opponent"))));
                            }
                            break;
                        }
                        case "docket":{
                            if (currentBinder != null){
                                currentDocket = create(new Docket(currentBinder, courtMap.get(attributes.getValue("court")), attributes.getValue("reference")));
                            }
                            break;
                        }
                        case "decision":{
                            if (currentDocket != null){
                                currentDecision = create(new Decision(currentDocket, attributes.getValue("reference"), LocalDate.parse(attributes.getValue("judgmentDate")), DecisionLevel.valueOf(attributes.getValue("level")), RecordNature.valueOf(attributes.getValue("recordNature"))));
                            }
                            break;
                        }
                        case "document":{
                            if (currentDecision != null){
                                create(new Document(currentDecision, Locale.forLanguageTag(attributes.getValue("locale")), pdfMap.get(attributes.getValue("pdf"))));
                            }
                            break;
                        }
                        case "analysis":{
                            if (currentDecision != null){
                                create(new Analysis(currentDecision, polMap.get(attributes.getValue("pol"))));
                            }
                            break;
                        }
                        case "trademark":{
                            if (currentBinder != null){
                                final Trademark trademark = create(new Trademark(currentBinder, Boolean.parseBoolean(attributes.getValue("opponent")), attributes.getValue("name")));
                                if (attributes.getValue("images") != null){
                                    updateImageIds(trademark, attributes.getValue("images").split(","));
                                }
                                final String ref = attributes.getValue("ref");
                                if (ref != null){
                                    rightMap.put(String.format("%s-%s", currentBinder.getId(), ref), trademark);
                                }
                            }
                            break;
                        }
                        case "patent":{
                            if (currentBinder != null){
                                final Patent patent = create(new Patent(currentBinder, Boolean.parseBoolean(attributes.getValue("opponent")), attributes.getValue("name")));
                                if (attributes.getValue("images") != null){
                                    updateImageIds(patent, attributes.getValue("images").split(","));
                                }
                                final String ref = attributes.getValue("ref");
                                if (ref != null){
                                    rightMap.put(String.format("%s-%s", currentBinder.getId(), ref), patent);
                                }
                            }
                            break;
                        }
                        case "honor":{
                            if (currentDecision != null && currentBinder != null){
                                create(new Honor(currentDecision, rightMap.get(String.format("%s-%s", currentBinder.getId(), attributes.getValue("right"))), RightValidity.valueOf(attributes.getValue("validity"))));
                            }
                            break;
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    switch (qName){
                        case "user":{
                            if (currentUser != null){
                                currentUser.setUserRoles(roles.stream().map(role -> new UserRole(currentUser, role)).collect(Collectors.toSet()));
                                update(currentUser);
                            }
                            currentUser = null;
                            break;
                        }
                        case "binder":{
                            LOGGER.info("binder created: {}", currentBinder.getReference());
                            currentBinder = null;
                            break;
                        }
                        case "docket":{
                            LOGGER.info("docket created: {}", currentDocket.getReference());
                            currentDocket = null;
                            break;
                        }
                        case "decision":{
                            LOGGER.info("decision created: {}", currentDecision.getReference());
                            currentDecision = null;
                            break;
                        }
                    }
                }

            });
        }
    }
}
