import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[4];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
	  grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
	  for (int r = 0; r < grid.length; r++) {
		  for (int c = 0; c < grid[0].length; c++) {
			  if (grid[r][c] == EMPTY) {
				  display.setColor(r, c, Color.BLACK);
			  }
			  else if (grid[r][c] == METAL) {
				  display.setColor(r, c, Color.GRAY);
			  }
			  else if (grid[r][c] == SAND) {
				  display.setColor(r, c, Color.YELLOW);
			  }
			  else if (grid[r][c] == WATER) {
				  display.setColor(r, c, Color.BLUE);
			  }
		  }
	  }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {

	  int randRow = new Random().nextInt(grid.length - 1);
	  int randCol = new Random().nextInt(grid[0].length);
	  
	  if (grid[randRow][randCol] == SAND) {
		  if (grid[randRow + 1][randCol] == EMPTY) {
			  grid[randRow][randCol] = EMPTY;
			  grid[randRow + 1][randCol] = SAND;
		  }
		  if (grid[randRow + 1][randCol] == WATER) {
			  grid[randRow + 1][randCol] = SAND;
			  grid[randRow][randCol] = WATER;
		  }
	  }
	  
	  else if (grid[randRow][randCol] == WATER) {
		  
		  ArrayList<Integer> moves = new ArrayList<Integer>();
		  
		  if (grid[randRow + 1][randCol] == EMPTY) {
			  moves.add(0);
		  }
		  if (randCol > 0 && randCol < (grid[0].length - 1)) {
			  if (grid[randRow][randCol - 1] == EMPTY) {
				  moves.add(1);
			  }
			  if (grid[randRow][randCol + 1] == EMPTY) {
				  moves.add(2);
			  }
		  }
		  else if (randCol == 0) { 
			  if (grid[randRow][randCol + 1] == EMPTY) {
				  moves.add(2);
			  }
		  }
		  else if (randCol == (grid[0].length - 1)) {
			  if (grid[randRow][randCol - 1] == EMPTY) {
				  moves.add(1);
			  }
		  }

		  System.out.println(moves.size());
		  if (moves.size() != 0) {
		  	  int index = new Random().nextInt(moves.size());
		  	  int dir = moves.get(index);
			  grid[randRow][randCol] = EMPTY;
			  
			  if (dir == 0) {
				  grid[randRow + 1][randCol] = WATER;
			  }
			  else if (dir == 1) {
				  grid[randRow][randCol - 1] = WATER;
			  }
			  else if (dir == 2) {
				  grid[randRow][randCol + 1] = WATER;
			  }
			  moves.clear();
		  }
	  }
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
