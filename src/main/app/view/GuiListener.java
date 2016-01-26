package view;

public interface GuiListener {
	public void addPerformed();
	public void removePerformed(int[] index);
	
	public void newPerformed();
	public void savePerformed();
	public void saveAsPerformed();
	public void openPerformed();
	public void exitPerformed();
}
