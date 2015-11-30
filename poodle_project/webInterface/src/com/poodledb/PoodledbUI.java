	package com.poodledb;

import java.io.File;
import java.io.FileReader;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.client.ui.VNotification.EventListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

import SearchForm.GeneralSearchParameter;
import guis.NewEntryView;
import guis.SearchResultView;
import guis.SearchView;

@SuppressWarnings("serial")
@Theme("poodledb")
@DesignRoot
public class PoodledbUI extends UI {
	private Image logo;
	private final VerticalLayout layout = new VerticalLayout();
	private MenuBar navigationMenu;
	private static VerticalLayout content;
	private static ServerTableSelection serverTableSelection;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PoodledbUI.class)
	public static class Servlet extends VaadinServlet {
	}
	
	@Override
	protected void init(VaadinRequest request) {
		//Layouts
		layout.setMargin(true);
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		layout.setSpacing(true);
		layout.addStyleName("layoutStyle");

		setContent(layout);
		
		//Logo
		//Integration of Logo
		System.out.println();
		String basepath =  VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource1 = new FileResource(new File(basepath+"/WEB-INF/img/logo_poodle.png"));
		logo= new Image("",resource1);
		logo.setWidth("512px");

		
		//setup search view
		//initially start with search view
		//initial ContentContainer:
		content= new SearchView(this);
		content.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		

		//MenuBar: 
		navigationMenu = new NavigationBar(this);
		navigationMenu.addStyleName("navigationMenu");

		//Selection
		serverTableSelection = new ServerTableSelection(this);
		
		//whole Layout
		layout.addComponent(logo);
		layout.addComponent(navigationMenu);
		layout.addComponent(serverTableSelection);
		layout.addComponent(content);
	}
	
	public void modifyLayouttoResultView(){
		layout.removeComponent(logo);
		layout.removeComponent(serverTableSelection);
		layout.setComponentAlignment(navigationMenu, Alignment.TOP_LEFT);
	}
	public void modifyLayouttoSearchView(){
		layout.removeAllComponents();
		layout.addComponent(logo);
		layout.addComponent(navigationMenu);
		layout.addComponent(serverTableSelection);
		layout.addComponent(content);
	}
	
	public Layout getContentBox(){
		return content;
	}
	
	public void SetContentBox(Component view){
		content.removeAllComponents();
		content.addComponent(view);
	}
	public ServerTableSelection getServerTable(){
		return serverTableSelection;
	}
}