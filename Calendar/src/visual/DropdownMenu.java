package visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class DropdownMenu {
	
	private CustomJTextField searchField;
	
	private JPanel searchPanel;
	private JScrollPane searchScrollPane;
	private SpringLayout searchLayout;
	
	
	public DropdownMenu() {
		searchPanel = new JPanel();
		searchPanel.setBackground(Color.white);
		searchPanel.setVisible(true);
		
		searchScrollPane = new JScrollPane(searchPanel);
		searchScrollPane.setBackground(Color.white);
		searchScrollPane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
		searchScrollPane.setPreferredSize(new Dimension(210,300));
		searchScrollPane.setVisible(false);
		searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		searchLayout = new SpringLayout();
		searchPanel.setLayout(searchLayout);
		searchPanel.add(searchField);
		searchLayout.putConstraint(SpringLayout.NORTH, searchField, 10, SpringLayout.NORTH, searchPanel);
		
	}
	
	
	
	public CustomJTextField getSearchField() {
		return searchField;
	}

	public void setSearchField(CustomJTextField searchField) {
		this.searchField = searchField;
	}

	public JPanel getSearchPanel() {
		return searchPanel;
	}

	public void setSearchPanel(JPanel searchPanel) {
		this.searchPanel = searchPanel;
	}

	public JScrollPane getSearchScrollPane() {
		return searchScrollPane;
	}

	public void setSearchScrollPane(JScrollPane searchScrollPane) {
		this.searchScrollPane = searchScrollPane;
	}

	public SpringLayout getSearchLayout() {
		return searchLayout;
	}

	public void setSearchLayout(SpringLayout searchLayout) {
		this.searchLayout = searchLayout;
	}
	
}
