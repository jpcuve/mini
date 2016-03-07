package com.messio.mini.session;

import com.messio.mini.*;
import com.messio.mini.entity.*;
import com.messio.mini.util.Path;
import org.joda.time.LocalDate;
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
import java.util.*;

/**
 * Created by jpc on 01/04/14.
 */
@Startup
@Singleton(name = "mini/initial-data")
public class InitialData extends Crud {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitialData.class);
    @Inject
    private AssetManager assetManager;

    @PostConstruct
    public void init() throws IOException, ParserConfigurationException, SAXException {
        User system = findByNamedQuery(User.class, User.USER_BY_USERNAME, Crud.Parameters.build("username", User.SYSTEM_NAME)).stream().findFirst().orElse(null);
        if (system == null){
            system = create(new User(User.SYSTEM_NAME));
            final Preference systemRoot = create(new Preference(null, system.getId(), ""));
            final Preference folders = create(new Preference(systemRoot, system.getId(), "folders"));
            final Map<String, String> folderProperties = new HashMap<>();
            folderProperties.put("base", System.getProperty("java.io.tmpdir"));
            folderProperties.put("incoming", ".");
            folderProperties.put("invoices", ".");
            folderProperties.put("uploaded", ".");
            folders.setProperties(folderProperties);
            update(folders);

/*
            // build test data
            final File fResource = new File(getClass().getClassLoader().getResource("/").getFile());
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

                private void updateImageIds(long rightId, String[] refs){
                    final String[] uuids = new String[refs.length];
                    for (int i = 0; i < refs.length; i++) uuids[i] = imageMap.get(refs[i]);
                    rightDao.updateImageIds(rightId, uuids);
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    switch(qName){
                        case "user":{
                            currentUser = userDao.insert(attributes.getValue("username"), Locale.forLanguageTag(attributes.getValue("locale")));
                            userDao.updatePassword(currentUser.getId(), attributes.getValue("password"));
                            roles.clear();
                            break;
                        }
                        case "role":{
                            roles.add(Role.valueOf(attributes.getValue("name")));
                            break;
                        }
                        case "court":{
                            courtMap.put(attributes.getValue("ref"), courtDao.insert(new Path<>(attributes.getValue("path").split(","))));
                            break;
                        }
                        case "pol":{
                            polMap.put(attributes.getValue("ref"), polDao.insert(new Path<>(attributes.getValue("path").split(","))));
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
                            memberMap.put(attributes.getValue("ref"), memberDao.insert(attributes.getValue("name")));
                            break;
                        }
                        case "binder":{
                            currentBinder = binderDao.insert(attributes.getValue("reference"), FirstAction.valueOf(attributes.getValue("firstAction")));
                            break;
                        }
                        case "party":{
                            if (currentBinder != null) binderMemberDao.insert(currentBinder.getId(), memberMap.get(attributes.getValue("member")).getId(), Boolean.parseBoolean(attributes.getValue("opponent")));
                            break;
                        }
                        case "docket":{
                            if (currentBinder != null) currentDocket = docketDao.insert(currentBinder.getId(), courtMap.get(attributes.getValue("court")).getId(), attributes.getValue("reference"));
                            break;
                        }
                        case "decision":{
                            if (currentDocket != null) currentDecision = decisionDao.insert(currentDocket.getId(), attributes.getValue("reference"), LocalDate.parse(attributes.getValue("judgmentDate")), DecisionLevel.valueOf(attributes.getValue("level")), RecordNature.valueOf(attributes.getValue("recordNature")));
                            break;
                        }
                        case "document":{
                            if (currentDecision != null) documentDao.insert(currentDecision.getId(), Locale.forLanguageTag(attributes.getValue("locale")), pdfMap.get(attributes.getValue("pdf")));
                            break;
                        }
                        case "analysis":{
                            if (currentDecision != null) analysisDao.insert(currentDecision.getId(), polMap.get(attributes.getValue("pol")).getId());
                            break;
                        }
                        case "trademark":{
                            if (currentBinder != null){
                                final Trademark trademark = trademarkDao.insert(currentBinder.getId(), Boolean.parseBoolean(attributes.getValue("opponent")), attributes.getValue("name"));
                                if (attributes.getValue("images") != null) updateImageIds(trademark.getId(), attributes.getValue("images").split(","));
                                final String ref = attributes.getValue("ref");
                                if (ref != null) rightMap.put(String.format("%s-%s", currentBinder.getId(), ref), trademark);
                            }
                            break;
                        }
                        case "patent":{
                            if (currentBinder != null){
                                final Patent patent = patentDao.insert(currentBinder.getId(), Boolean.parseBoolean(attributes.getValue("opponent")), attributes.getValue("name"));
                                if (attributes.getValue("images") != null) updateImageIds(patent.getId(), attributes.getValue("images").split(","));
                                final String ref = attributes.getValue("ref");
                                if (ref != null) rightMap.put(String.format("%s-%s", currentBinder.getId(), ref), patent);
                            }
                            break;
                        }
                        case "honor":{
                            if (currentDecision != null && currentBinder != null){
                                honorDao.insert(currentDecision.getId(), rightMap.get(String.format("%s-%s", currentBinder.getId(), attributes.getValue("right"))).getId(), RightValidity.valueOf(attributes.getValue("validity")));
                            }
                            break;
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    switch (qName){
                        case "user":{
                            if (currentUser != null) userDao.updateRoles(currentUser.getId(), roles);
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
*/
        }
    }

}
