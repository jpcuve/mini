package com.messio.mini;

import com.messio.mini.bean.AssetManager;
import com.messio.mini.domain.*;
import com.messio.mini.util.Path;
import com.messio.mini.web.AssetServlet;
import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@ServletComponentScan
public class MiniApplication extends SpringBootServletInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(MiniApplication.class);
    private final Facade facade;
    private final AssetManager assetManager;
    private final ResourceLoader resourceLoader;

    public MiniApplication(@Autowired Facade facade, @Autowired AssetManager assetManager, @Autowired ResourceLoader resourceLoader) {
        this.facade = facade;
        this.assetManager = assetManager;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init(){
        if (facade.countBinderIds() == 0){
            final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            try{
                final SAXParser saxParser = saxParserFactory.newSAXParser();
                saxParser.parse(getClass().getClassLoader().getResourceAsStream("init.xml"), new DefaultHandler(){
                    private Map<String, Court> courtMap = new HashMap<>();
                    private Map<String, Pol> polMap = new HashMap<>();
                    private Map<String, String> imageMap = new HashMap<>();
                    private Map<String, String> pdfMap = new HashMap<>();
                    private Map<String, Actor> actorMap = new HashMap<>();
                    private Map<String, Right> rightMap = new HashMap<>();
                    private Binder currentBinder = null;
                    private Docket currentDocket = null;
                    private Decision currentDecision = null;

                    private void updateImageIds(Right right, String[] refs){
                        final List<String> uuids = new ArrayList<>();
                        for (String ref: refs){
                            uuids.add(imageMap.get(ref));
                        }
                        right.setImageIds(uuids);
                        facade.update(right);
                    }

                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                        switch(qName){
                            case "court":{
                                Court parent = null;
                                for (final String courtName: new Path<>(attributes.getValue("path").split(",")).getPath()){
                                    final Court p = parent;
                                    parent = facade.findCourtsByParentByName(p, courtName)
                                            .stream()
                                            .findFirst()
                                            .orElseGet(() -> facade.create(new Court(p, courtName)));
                                }
                                courtMap.put(attributes.getValue("ref"), parent);
                                break;
                            }
                            case "pol":{
                                Pol parent = null;
                                for (final String polName: new Path<>(attributes.getValue("path").split(",")).getPath()){
                                    final Pol p = parent;
                                    parent = facade.findPolsByParentByName(p, polName)
                                            .stream()
                                            .findFirst()
                                            .orElseGet(() -> facade.create(new Pol(p, polName)));
                                }
                                polMap.put(attributes.getValue("ref"), parent);
                                break;
                            }
                            case "image":{
                                try{
                                    final Resource resource = resourceLoader.getResource("classpath:assets/" + attributes.getValue("asset"));
                                    imageMap.put(attributes.getValue("ref"), assetManager.saveData(attributes.getValue("mime"), resource.contentLength(), resource.getInputStream(), resource.getFilename()));
                                } catch (IOException x){
                                    throw new SAXException(x);
                                }
                                break;
                            }
                            case "pdf":{
                                try{
                                    final Resource resource = resourceLoader.getResource("classpath:assets/" + attributes.getValue("asset"));
                                    pdfMap.put(attributes.getValue("ref"), assetManager.saveData("application/pdf", resource.contentLength(), resource.getInputStream(), resource.getFilename()));
                                } catch (IOException x){
                                    throw new SAXException(x);
                                }
                                break;
                            }
                            case "actor":{
                                actorMap.put(attributes.getValue("ref"), facade.create(new Actor(attributes.getValue("name"))));
                                break;
                            }
                            case "binder":{
                                Set<Domain> domains = Arrays.stream(attributes.getValue("domains").split(",")).map(Domain::valueOf).collect(Collectors.toSet());
                                currentBinder = facade.create(new Binder(attributes.getValue("reference"), Area.valueOf(attributes.getValue("area")), domains));
                                if (attributes.getValue("firstAction") != null) currentBinder.setFirstAction(FirstAction.valueOf(attributes.getValue("firstAction")));
                                if (attributes.getValue("firstActionDate") != null) currentBinder.setFirstActionDate(LocalDate.parse(attributes.getValue("firstActionDate")));
                                currentBinder = facade.update(currentBinder);
                                break;
                            }
                            case "party":{
                                if (currentBinder != null){
                                    facade.create(new Party(currentBinder, actorMap.get(attributes.getValue("actor")), Boolean.parseBoolean(attributes.getValue("plaintiff"))));
                                }
                                break;
                            }
                            case "docket":{
                                if (currentBinder != null){
                                    currentDocket = facade.create(new Docket(currentBinder, courtMap.get(attributes.getValue("court")), attributes.getValue("reference")));
                                }
                                break;
                            }
                            case "decision":{
                                if (currentDocket != null){
                                    currentDecision = facade.create(new Decision(currentDocket, attributes.getValue("reference"), LocalDate.parse(attributes.getValue("judgmentDate")), DecisionLevel.valueOf(attributes.getValue("level")), RecordNature.valueOf(attributes.getValue("recordNature"))));
                                }
                                break;
                            }
                            case "document":{
                                if (currentDecision != null){
                                    facade.create(new Document(currentDecision, Locale.forLanguageTag(attributes.getValue("locale")), pdfMap.get(attributes.getValue("pdf"))));
                                }
                                break;
                            }
                            case "analysis":{
                                if (currentDecision != null){
                                    facade.create(new Analysis(currentDecision, polMap.get(attributes.getValue("pol"))));
                                }
                                break;
                            }
                            case "trademark":{
                                if (currentBinder != null){
                                    final Trademark trademark = facade.create(new Trademark(currentBinder, Boolean.parseBoolean(attributes.getValue("plaintiff")), attributes.getValue("name")));
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
                            case "domain-name":{
                                if (currentBinder != null){
                                    final DomainName domainName = facade.create(new DomainName(currentBinder, Boolean.parseBoolean(attributes.getValue("plaintiff")), attributes.getValue("name")));
                                    final String ref = attributes.getValue("ref");
                                    if (ref != null){
                                        rightMap.put(String.format("%s-%s", currentBinder.getId(), ref), domainName);
                                    }
                                }
                                break;
                            }
                            case "patent":{
                                if (currentBinder != null){
                                    final Patent patent = facade.create(new Patent(currentBinder, Boolean.parseBoolean(attributes.getValue("plaintiff")), attributes.getValue("name")));
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
                            case "design-model":{
                                if (currentBinder != null){
                                    final DesignModel designModel = facade.create(new DesignModel(currentBinder, Boolean.parseBoolean(attributes.getValue("plaintiff")), attributes.getValue("name")));
                                    if (attributes.getValue("images") != null){
                                        updateImageIds(designModel, attributes.getValue("images").split(","));
                                    }
                                    final String ref = attributes.getValue("ref");
                                    if (ref != null){
                                        rightMap.put(String.format("%s-%s", currentBinder.getId(), ref), designModel);
                                    }
                                }
                                break;
                            }
                            case "honor":{
                                if (currentDecision != null && currentBinder != null){
                                    facade.create(new Honor(currentDecision, rightMap.get(String.format("%s-%s", currentBinder.getId(), attributes.getValue("right"))), RightValidity.valueOf(attributes.getValue("validity"))));
                                }
                                break;
                            }
                        }
                    }

                    @Override
                    public void endElement(String uri, String localName, String qName) throws SAXException {
                        switch (qName){
                            case "binder":{
                                LOGGER.debug("binder created: {}", currentBinder.getReference());
                                currentBinder = null;
                                break;
                            }
                            case "docket":{
                                LOGGER.debug("docket created: {}", currentDocket.getReference());
                                currentDocket = null;
                                break;
                            }
                            case "decision":{
                                LOGGER.debug("decision created: {}", currentDecision.getReference());
                                currentDecision = null;
                                break;
                            }
                        }
                    }

                });
            } catch(IOException | SAXException | ParserConfigurationException e){
                LOGGER.error("Cannot initialize database", e);
            }
        }
    }

    @Bean
    public ServletRegistrationBean assetServlet(){
        final ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new AssetServlet(assetManager), "/asset");
        return servletRegistrationBean;
    }

    @Bean
    ServletRegistrationBean h2servletRegistration(){
        final ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet(), "/h2-console/*");
        return registrationBean;
    }

    public static void main(String[] args) {
		SpringApplication.run(MiniApplication.class, args);
    }
}
